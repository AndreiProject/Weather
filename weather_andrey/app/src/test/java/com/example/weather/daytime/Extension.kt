package com.example.weather.daytime

import java.time.*
import java.util.*

internal fun LocalDateTime.toDate() : Date {
    val zone = atZone(ZoneId.systemDefault())
   return Date.from(zone.toInstant())
}