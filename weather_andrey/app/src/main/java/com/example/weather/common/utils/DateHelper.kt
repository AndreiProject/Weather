package com.example.weather.common.utils

import java.util.*

const val DAY_OF_WEEK_PATTERN = "dd.MM.y E"
const val DAY_TIME_PATTERN = "dd.MM.y HH:mm"
const val DAY_FULL_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

fun getLocal(): Locale {
    return Locale("ru")
}