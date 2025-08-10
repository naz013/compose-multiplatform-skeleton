package com.github.naz013.localization

import com.github.naz013.localization.AppStrings.loadStrings
import com.github.naz013.localization.resources.Res
import com.github.naz013.logging.Logger
import kotlinx.serialization.json.Json

private const val TAG = "StringResources"

/**
 * Defines the names of the JSON files expected within each locale's directory.
 * Add or remove file names here as your localization structure evolves.
 */
private val JSON_FILE_NAMES_PER_LOCALE = listOf(
    "main.json",
)

private val SUPPORTED_LOCALES = listOf(
    AppLocale("en"),
    AppLocale("es"),
    AppLocale("fr"),
    AppLocale("pl"),
)

/**
 * Defines all the localized strings for the application.
 *
 * This object now acts as a central repository for all string resources,
 * loaded dynamically from JSON files placed in `commonMain/composeResources/files/localization/`.
 *
 * To add a new language:
 * 1. Create a new JSON file (e.g., `de.json`) in `commonMain/composeResources/files/localization/`.
 * 2. Add the new `AppLocale` to the `localesToLoad` list within the `loadStrings` function.
 *
 * Keys should be consistent across all locales (e.g., "welcome_message").
 * Values are the actual translated strings.
 *
 * String formatting with arguments is supported using placeholders like {0}, {1}, etc.
 * Example in JSON: "Hello, {0}! Today is {1}."
 */
object AppStrings {
    // Internal mutable map to store the loaded localized strings.
    private val _localizedStrings: MutableMap<AppLocale, Map<String, String>> = mutableMapOf()

    /**
     * Provides a read-only view of the loaded localized strings.
     * This map will be populated after [loadStrings] is called.
     */
    val localizedStrings: Map<AppLocale, Map<String, String>>
        get() = _localizedStrings

    /**
     * Provides a list of all available locales that have been successfully loaded.
     * This can be used to populate language selection UI elements.
     */
    val availableLocales: List<AppLocale>
        get() = _localizedStrings.keys.toList()

    /**
     * Loads localized strings from JSON files.
     * Each locale should have a corresponding JSON file (e.g., "main.json", "main.json")
     * located in `commonMain/composeResources/files/localization/`.
     *
     * This function should be called once at the application startup.
     */
    suspend fun loadStrings() {
        _localizedStrings.clear()

        SUPPORTED_LOCALES.forEach { locale ->
            val mergedStringsForLocale = mutableMapOf<String, String>()
            var loadedSuccessfullyAtLeastOneFile = false // Track if any file was loaded for the locale

            JSON_FILE_NAMES_PER_LOCALE.forEach { fileName ->
                val filePath = "files/localization/${locale.languageCode}/$fileName"
                try {
                    // Read the JSON content using the platform-specific resource reader
                    val jsonString = Res.readBytes(filePath).decodeToString()
                    val stringsMap = Json.decodeFromString<Map<String, String>>(jsonString)
                    mergedStringsForLocale.putAll(stringsMap) // Merge strings from this file into the locale's map
                    Logger.i(TAG, "Successfully loaded strings (${stringsMap.size}) for locale: $locale from $filePath")
                    loadedSuccessfullyAtLeastOneFile = true
                } catch (e: Exception) {
                    Logger.w(TAG, "Warning: Could not load or parse '$filePath' for locale '$locale': ${e.message}")
                    // Don't re-throw, continue to try other files for this locale
                }
            }

            if (loadedSuccessfullyAtLeastOneFile) {
                _localizedStrings[locale] = mergedStringsForLocale
            } else {
                Logger.w(TAG, "Error: No localized strings were loaded for locale: $locale. Check files in commonMain/resources/localization/${locale.languageCode}/ and ensure they are correct JSON format.")
            }
        }
        if (_localizedStrings.isEmpty()) {
            Logger.w(TAG, "Warning: No localized strings were loaded for any locale! Check commonMain/resources/localization/ and `localesToLoad`.")
        }
    }
}
