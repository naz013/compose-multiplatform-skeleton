package com.github.naz013.kmparch

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Multiplatform Skeleton",
    ) {
        initKoin {
            printLogger()
        }
        App()
    }
}