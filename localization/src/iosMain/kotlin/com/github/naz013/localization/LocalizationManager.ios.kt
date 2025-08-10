package com.github.naz013.localization

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.localeIdentifier

actual fun getSystemLocaleOrNull(): AppLocale? {
    return try {
        val systemLocale = NSLocale.currentLocale
        val languageCode = systemLocale.languageCode
        // iOS locale identifiers can be complex (e.g., "en_US@calendar=gregorian").
        // We extract the language and an optional country code.
        val localeIdentifier = systemLocale.localeIdentifier
        val countryCode = if (localeIdentifier.contains("_")) {
            localeIdentifier.substringAfter("_").substringBefore("@", "").takeIf { it.isNotEmpty() }
        } else {
            null
        }

        AppLocale(
            languageCode = languageCode,
            countryCode = countryCode
        )
    } catch (e: Exception) {
        println("Error getting system locale on iOS: ${e.message}")
        null
    }
}
