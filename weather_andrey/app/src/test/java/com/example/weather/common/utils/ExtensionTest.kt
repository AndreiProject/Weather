package com.example.weather.common.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.text.capitalize

class ExtensionTest {

    @Test
    fun `check formatToString DayOfWeek pattern`() {
        val date = Date(1638424800000) // 2021-12-02 09:00:00
        val dateFormat = date.formatToString(DAY_OF_WEEK_PATTERN)

        val dateFormatEx = "02.12.2021 чт"
        assertEquals(dateFormatEx, dateFormat)
    }

    @Test
    fun `check formatToString DayTime pattern`() {
        val date = Date(1638424800000) // 2021-12-02 09:00:00
        val dateFormat = date.formatToString(DAY_TIME_PATTERN)

        val dateFormatEx = "02.12.2021 09:00"
        assertEquals(dateFormatEx, dateFormat)
    }

    @Test
    fun `check convertToDate DayFullTime pattern`() {
        val dateText = "2021-12-02 09:00:00"
        val date = dateText.convertToDate(DAY_FULL_TIME_PATTERN)

        val dateEx = Date(1638424800000) // 2021-12-02 09:00:00
        assertEquals(dateEx, date)
    }

    @Test
    fun `check capitalize`() {
        val text = "тамбов"
        val textCapitalize = text.capitalize()

        val textEx = "Тамбов"
        assertEquals(textEx, textCapitalize)
    }
}