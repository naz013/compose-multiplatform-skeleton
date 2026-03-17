package com.github.naz013.datetime

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun LocalTime.Companion.now(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalTime {
    return Clock.System.now().toLocalDateTime(timeZone).time
}
