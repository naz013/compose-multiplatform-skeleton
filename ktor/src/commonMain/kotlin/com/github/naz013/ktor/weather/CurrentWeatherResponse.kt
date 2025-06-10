package com.github.naz013.ktor.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentWeatherResponse(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("current_units")
    val units: CurrentUnits,
    @SerialName("current")
    val current: CurrentTemperature
)

@Serializable
internal data class CurrentUnits(
    @SerialName("temperature_2m")
    val temperature: String
)

@Serializable
internal data class CurrentTemperature(
    @SerialName("temperature_2m")
    val temperature: Float
)
