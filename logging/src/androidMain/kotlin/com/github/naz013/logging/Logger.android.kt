package com.github.naz013.logging

import android.util.Log

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Logger {
    actual fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    actual fun e(tag: String, message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }

    actual fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    actual fun w(tag: String, message: String, throwable: Throwable) {
        Log.w(tag, message, throwable)
    }

    actual fun v(tag: String, message: String) {
        Log.v(tag, message)
    }
}
