package com.github.naz013.localization

import kotlinx.coroutines.flow.Flow

interface LocalizationSettings {

    /**
     * A Flow that emits the current locale whenever it changes.
     * This allows for real-time updates to the UI.
     */
    val localeFlow: Flow<String>

    /**
     * Sets the locale.
     * This is a suspend function to allow for asynchronous data storage.
     */
    suspend fun setLocale(locale: String)

    /**
     * Retrieves the current locale.
     * This is a suspend function to allow for asynchronous data retrieval.
     */
    suspend fun getLocale(): String
}