package com.github.naz013.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDate.Companion.now(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDate {
    return Clock.System.now().toLocalDateTime(timeZone).date
}
