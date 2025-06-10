package com.github.naz013.ktor.weather

interface CurrentWeatherApi {
    suspend fun getTemperature(): String?
}
