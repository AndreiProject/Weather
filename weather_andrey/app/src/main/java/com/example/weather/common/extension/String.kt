package com.example.weather.common.extension

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