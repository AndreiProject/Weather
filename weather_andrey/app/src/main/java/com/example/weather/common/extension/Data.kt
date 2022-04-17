package com.example.weather.common.extension

import java.text.SimpleDateFormat
import java.util.*

const val DAY_OF_WEEK_PATTERN = "dd.MM.y E"
const val DAY_TIME_PATTERN = "dd.MM.y HH:mm"
const val DAY_FULL_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

fun Date.formatToString(pattern: String): String {
    val df = SimpleDateFormat(pattern, getLocal())
    return df.format(time)
}

fun getLocal(): Locale {
    return Locale("ru")
}