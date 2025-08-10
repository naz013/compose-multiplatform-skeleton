package com.github.naz013.localization

/**
 * Represents a simplified application locale.
 *
 * @param languageCode The ISO 639-1 two-letter language code (e.g., "en", "es", "fr").
 * @param countryCode The ISO 3166-1 alpha-2 two-letter country code (optional, e.g., "US", "GB").
 * If null, it refers to the general language.
 */
data class AppLocale(
    val languageCode: String,
    val countryCode: String? = null
) {
    /**
     * Returns a string representation of the locale, e.g., "en", "es-ES".
     */
    override fun toString(): String {
        return if (countryCode != null) "$languageCode-$countryCode" else languageCode
    }

    // Allows easy comparison with platform-specific locales if needed.
    // This can be useful if the system locale might have a country code
    // but we only have a language-specific translation.
    fun matches(otherLanguageCode: String, otherCountryCode: String? = null): Boolean {
        return this.languageCode == otherLanguageCode && this.countryCode == otherCountryCode
    }
}
