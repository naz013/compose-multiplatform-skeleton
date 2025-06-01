package com.github.naz013.kmparch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform