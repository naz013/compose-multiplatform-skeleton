package com.github.naz013.logging

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Logger {
    actual fun d(tag: String, message: String) {
        println("$tag: $message")
    }

    actual fun e(tag: String, message: String) {
        println("$tag: $message")
    }

    actual fun e(tag: String, message: String, throwable: Throwable) {
        println("$tag: $message")
        throwable.printStackTrace()
    }

    actual fun i(tag: String, message: String) {
        println("$tag: $message")
    }

    actual fun w(tag: String, message: String) {
        println("$tag: $message")
    }

    actual fun w(tag: String, message: String, throwable: Throwable) {
        println("$tag: $message")
        throwable.printStackTrace()
    }

    actual fun v(tag: String, message: String) {
        println("$tag: $message")
    }
}
