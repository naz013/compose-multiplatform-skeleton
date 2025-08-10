package com.github.naz013.localization.util

import com.github.naz013.localization.LocalizableString
import com.github.naz013.localization.StringKeys

/**
 * Expect declaration for a platform-specific function that retrieves all
 * [LocalizableString] keys from the [StringKeys] object using reflection.
 *
 * This function will be implemented differently on each platform (Android, iOS, Desktop).
 */
expect fun getAllStringKeysForVerification(): List<String>

/**
 * Expect declaration for a platform-specific function to print an error message.
 *
 * This function will be implemented differently on each platform (Android, iOS, Desktop).
 *
 * @param message The error message to be printed.
 */
expect fun printError(message: String)

/**
 * A utility to verify the uniqueness of keys defined in [StringKeys].
 * This function can be executed as part of a Gradle task to ensure no duplicate
 * localization keys exist in your StringKeys object.
 */
fun verifyUniqueStringKeys() {
    val keys = getAllStringKeysForVerification() // Use the expect function
    val distinctKeys = keys.distinct()

    if (keys.size != distinctKeys.size) {
        val duplicates = keys.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        printError("ERROR: Duplicate string keys found in StringKeys object:")
        duplicates.forEach { printError("- '$it'") }
        throw IllegalStateException("StringKeys contain duplicate keys: $duplicates")
    } else {
        println("All StringKeys are unique.")
    }
}

// Main function to allow running this verification via a Gradle task
fun main() {
    verifyUniqueStringKeys()
}
