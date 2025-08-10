package com.github.naz013.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * CompositionLocal for providing the [LocalizationManager] instance down the Composable tree.
 * This avoids prop-drilling and makes the manager easily accessible.
 */
val LocalLocalizationManager = staticCompositionLocalOf<LocalizationManager> {
    error("No LocalizationManager provided")
}

@Composable
fun localizedString(localizableString: LocalizableString, vararg args: Any): String {
    return LocalLocalizationManager.current.getString(localizableString, *args)
}

/**
 * Manages the application's current locale and provides localized strings.
 * This class acts as the central hub for all localization-related logic.
 *
 * It uses a [StateFlow] to broadcast changes in the current locale,
 * allowing UI components to reactively update.
 *
 * @property defaultLocale The fallback locale to use if a string is not found
 * for the current locale or if system locale detection fails.
 */
class LocalizationManager(
    private val localizationSettings: LocalizationSettings,
    private val defaultLocale: AppLocale = AppLocale("en") // Default to English if no system locale or string not found
) {
    // MutableStateFlow to hold the current locale, allows updates
    private val _currentLocale = MutableStateFlow(defaultLocale)
    // Exposed as a read-only StateFlow for UI observation
    val currentLocale: StateFlow<AppLocale> = _currentLocale.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        runBlocking {
            AppStrings.loadStrings()
        }
        coroutineScope.launch {
            val currentLocale = localizationSettings.getLocale()
                .takeIf { it.isNotEmpty() }
                ?.let { AppLocale(it) }
            initSystemLocale(currentLocale)
            localizationSettings.localeFlow.collect { locale ->
                if (locale.isNotEmpty()) {
                    initSystemLocale(AppLocale(locale))
                }
            }
        }
    }

    /**
     * Attempts to initialize the current locale based on the system's locale.
     * This function should be called once, early in the application's lifecycle,
     * typically after [AppStrings.loadStrings] has completed.
     *
     * If system locale detection fails or a full match is not available,
     * it tries to match by language code only. If no match is found,
     * it falls back to the [defaultLocale].
     */
    fun initSystemLocale(appLocale: AppLocale? = null) {
        val systemLocale = appLocale ?: getSystemLocaleOrNull()
        if (systemLocale != null) {
            // First, try to find an exact match including country code if present
            if (AppStrings.availableLocales.contains(systemLocale)) {
                _currentLocale.value = systemLocale
                println("Initialized with exact system locale: ${systemLocale}")
                return
            }

            // If no exact match, try to find a match based on language code only
            val systemLanguageOnly = systemLocale.languageCode
            val matchingLocale = AppStrings.availableLocales.firstOrNull {
                it.languageCode == systemLanguageOnly
            }
            if (matchingLocale != null) {
                _currentLocale.value = matchingLocale
                println("Initialized with system language-only locale: ${matchingLocale}")
                return
            }
        }

        // If system locale could not be determined or no matching locale found, fall back to default
        _currentLocale.value = defaultLocale
        println("System locale not supported or found, falling back to default: ${defaultLocale}")
    }

    /**
     * Sets the application's current locale.
     *
     * If the [newLocale] is not available in [AppStrings], it attempts to
     * fall back to a closest match (language only) or the [defaultLocale].
     *
     * @param newLocale The new [AppLocale] to set.
     */
    fun setLocale(newLocale: AppLocale) {
        if (AppStrings.availableLocales.contains(newLocale)) {
            _currentLocale.value = newLocale
            println("Locale set to: $newLocale")
        } else {
            println("Warning: Full locale $newLocale is not directly available in AppStrings. Attempting fallback.")
            // Optionally, fall back to default or a closest match (language only)
            val closestMatch = AppStrings.availableLocales.firstOrNull { it.languageCode == newLocale.languageCode }
            if (closestMatch != null) {
                _currentLocale.value = closestMatch
                println("Falling back to closest language match: $closestMatch")
            } else {
                _currentLocale.value = defaultLocale
                println("Falling back to default locale: ${defaultLocale} (as no language match found for $newLocale)")
            }
        }
    }

    /**
     * Retrieves a localized string for the current locale using its key.
     * Supports string formatting with positional arguments ({0}, {1}, etc.).
     *
     * If the string key is not found for the current locale, it attempts to
     * retrieve it from the [defaultLocale]. If still not found, it returns
     * a placeholder indicating the missing key.
     *
     * @param localizableString The unique identifier for the string.
     * @param args Variable number of arguments for string formatting.
     * @return The localized and formatted string.
     */
    fun getString(localizableString: LocalizableString, vararg args: Any): String {
        // If the string is marked as not translatable, return its key directly.
        // For example, StringKeys.English.key is "English", which is its fixed display value.
        if (!localizableString.translatable) {
            val defaultLocaleStrings = AppStrings.localizedStrings[defaultLocale]
                ?.get(localizableString.key)
                ?: run {
                    println("Warning: Default locale string not found for key: ${localizableString.key}")
                    return "MISSING_STRING_FOR_KEY: ${localizableString.key}"
                }
            return if (args.isNotEmpty()) {
                formatString(defaultLocaleStrings, *args)
            } else {
                defaultLocaleStrings
            }
        }

        val key = localizableString.key // Get the actual string key for lookup

        // Try to get the string from the current locale's map
        val currentLocaleStrings = AppStrings.localizedStrings[_currentLocale.value]
        var localizedString = currentLocaleStrings?.get(key)

        // If not found in current locale, try the default locale
        if (localizedString == null) {
            val defaultLocaleStrings = AppStrings.localizedStrings[defaultLocale]
            localizedString = defaultLocaleStrings?.get(key)
        }

        // If still not found after trying current and default locales, return a clear error message.
        val finalString = localizedString ?: run {
            println("Warning: String not found for key: $key")
            return "MISSING_STRING_FOR_KEY: $key"
        }

        return if (args.isNotEmpty()) {
            formatString(finalString, *args)
        } else {
            finalString
        }
    }

    /**
     * Formats a string by replacing positional placeholders (e.g., "{0}", "{1}")
     * with the provided arguments.
     *
     * @param format The format string containing placeholders.
     * @param args The arguments to insert into the format string.
     * @return The formatted string.
     */
    private fun formatString(format: String, vararg args: Any): String {
        var formatted = format
        args.forEachIndexed { index, arg ->
            formatted = formatted.replace("{$index}", arg.toString())
        }
        return formatted
    }
}

/**
 * Expect declaration for platform-specific system locale retrieval.
 *
 * This function is expected to be implemented differently on each platform
 * (Android, iOS, Desktop) to get the native system locale.
 * Returns null if the system locale cannot be determined or parsed.
 */
expect fun getSystemLocaleOrNull(): AppLocale?
