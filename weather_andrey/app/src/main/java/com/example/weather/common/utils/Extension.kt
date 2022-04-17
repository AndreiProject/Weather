package com.example.weather.common.utils

import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

fun String.capitalize(): String {
    return lowercase(Locale.getDefault())
        .replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(getLocal()) else it.toString()
        }
}

fun String.convertToDate(pattern: String): Date {
    val formatter = SimpleDateFormat(pattern, getLocal())
    return formatter.parse(this) ?: throw RuntimeException("Date is null")
}

fun Date.formatToString(pattern: String): String {
    val df = SimpleDateFormat(pattern, getLocal())
    return df.format(time)
}