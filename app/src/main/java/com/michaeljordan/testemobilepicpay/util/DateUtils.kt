package com.michaeljordan.testemobilepicpay.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun toSimpleString(date: Date) : String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }

    @JvmStatic
    fun toSimpleStringTime(date: Date) : String {
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date)
    }
}