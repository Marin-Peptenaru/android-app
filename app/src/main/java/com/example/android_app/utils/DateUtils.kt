package com.example.android_app.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

private val daysInMonth = mapOf<Int, Int>(
    1 to 31, 2 to 28, 3 to 31, 4 to 30,
    5 to 31, 6 to 30, 7 to 31, 8 to 31,
    9 to 30, 10 to 31, 11 to 30, 12 to 31
)

fun getNumberOfDaysInMonth(month: Int, year: Int): Int{
    return (daysInMonth[month]?: 0) + (if(month == 2 && year%4 == 0) 1 else 0)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.copy(year: Int? = null, month: Int? = null, day: Int? = null): LocalDate {
    return LocalDate.of(
        year ?: this.year,
        month ?: this.monthValue,
        day ?: this.dayOfMonth
    )
}
