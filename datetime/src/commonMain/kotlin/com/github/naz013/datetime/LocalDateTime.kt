package com.github.naz013.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.Companion.now(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

fun LocalDateTime.toEpochMilliseconds(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Long {
    return this.toInstant(timeZone).toEpochMilliseconds()
}
