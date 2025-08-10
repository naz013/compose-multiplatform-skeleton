package com.github.naz013.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for managing localization-related UI state and actions.
 *
 * It acts as an intermediary between the UI and the [LocalizationManager],
 * providing UI-friendly access to the current locale and actions to change it.
 *
 * @param localizationManager The core manager for localization logic.
 */
class LocalizationViewModel(
    private val localizationManager: LocalizationManager
) {
    /**
     * Exposes the current application locale as a [StateFlow] for UI observation.
     */
    val currentLocale: StateFlow<AppLocale> = localizationManager.currentLocale

    /**
     * Returns a list of all available [AppLocale]s that the application supports.
     * This list is derived from the successfully loaded strings in [AppStrings].
     */
    fun getAvailableLocales(): List<AppLocale> {
        return AppStrings.availableLocales // Now directly from AppStrings object
    }

    /**
     * Handles the event when a new locale is selected by the user.
     *
     * @param locale The [AppLocale] selected by the user.
     */
    fun onLocaleSelected(locale: AppLocale) {
        localizationManager.setLocale(locale)
    }

    /**
     * Provides a convenient way for Composables to access localized strings
     * through the ViewModel (though direct access via [LocalLocalizationManager]
     * is often preferred for strings).
     *
     * @param key The unique identifier for the string.
     * @param args Variable number of arguments for string formatting.
     * @return The localized and formatted string.
     */
    fun getString(localizableString: LocalizableString, vararg args: Any): String {
        return localizationManager.getString(localizableString, *args)
    }
}

/**
 * A simple factory function to remember and provide a [LocalizationViewModel]
 * within the Composable hierarchy. This can be extended for more complex
 * dependency injection setups.
 *
 * @param localizationManager The [LocalizationManager] instance to use.
 * @return A remembered instance of [LocalizationViewModel].
 */
@Composable
fun rememberLocalizationViewModel(localizationManager: LocalizationManager): LocalizationViewModel {
    return remember(localizationManager) {
        LocalizationViewModel(localizationManager)
    }
}
