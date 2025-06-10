package com.github.naz013.ktor.weather

import com.github.naz013.ktor.ClientFactory
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

internal class CurrentWeatherApiImpl(
    clientFactory: ClientFactory
) : CurrentWeatherApi {

    private val client by lazy { clientFactory.create() }

    override suspend fun getTemperature(): String? {
        val getCurrentWeather: CurrentWeatherResponse = client.get(CurrentWeatherRequest()).body()
        return getCurrentWeather.current.temperature.toString() + " " + getCurrentWeather.units.temperature
    }
}
