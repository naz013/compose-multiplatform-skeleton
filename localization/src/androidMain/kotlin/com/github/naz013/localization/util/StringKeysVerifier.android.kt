package com.github.naz013.localization.util

import com.github.naz013.localization.LocalizableString
import com.github.naz013.localization.StringKeys
import kotlin.reflect.full.memberProperties

actual fun getAllStringKeysForVerification(): List<String> {
    return StringKeys::class.memberProperties
        .filter { it.returnType.classifier == LocalizableString::class }
        .map { property ->
            val localizableString = property.call(StringKeys) as LocalizableString
            localizableString.key
        }
}

actual fun printError(message: String) {
    System.err.println(message)
}
