package com.github.naz013.ktor.weather

import io.ktor.resources.Resource

@Resource(path = "v1/forecast")
internal class CurrentWeatherRequest(
    val latitude: Double = 50.5,
    val longitude: Double = 30.5,
    val current: String = "temperature_2m"
)
