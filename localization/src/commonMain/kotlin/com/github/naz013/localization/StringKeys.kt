package com.github.naz013.localization

/**
 * Data class to represent a localizable string key with additional metadata.
 *
 * @param key The actual string identifier used in JSON translation files.
 * @param translatable A flag indicating whether this string should be translated.
 * If false, [getString] will directly return the [key] itself, meaning the key is the fixed display value.
 */
data class LocalizableString(
    val key: String,
    val translatable: Boolean = true // Default to true, meaning it should be translated via JSON
)

/**
 * Defines constant string keys used for localization.
 *
 * Each `val` here is an instance of [LocalizableString], providing:
 * - The actual `key` that matches the JSON file.
 * - A `translatable` flag:
 * - `true` for general UI text that will be looked up in JSON.
 * - `false` for language names (e.g., "English", "Polski") or other fixed texts.
 * For `translatable = false`, the `key` itself will be used as the display value,
 * and no JSON lookup will occur for that specific key.
 */
object StringKeys {
    val AppTitle = LocalizableString("app_title")
    // Add any new string keys here as const val
}
