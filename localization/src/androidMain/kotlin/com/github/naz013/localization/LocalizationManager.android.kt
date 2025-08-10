package com.github.naz013.localization

import java.util.Locale

actual fun getSystemLocaleOrNull(): AppLocale? {
    return try {
        val systemLocale = Locale.getDefault()
        AppLocale(
            languageCode = systemLocale.language,
            countryCode = systemLocale.country.takeIf { it.isNotEmpty() }
        )
    } catch (e: Exception) {
        println("Error getting system locale on Android: ${e.message}")
        null
    }
}
