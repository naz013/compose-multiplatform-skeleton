package com.github.naz013.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun LocalDate.Companion.now(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDate {
    return Clock.System.now().toLocalDateTime(timeZone).date
}
