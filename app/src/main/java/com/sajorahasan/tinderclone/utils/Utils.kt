package com.sajorahasan.tinderclone.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun formatDate(value: String): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        formatter.isLenient = true
        var parsedDate: Date? = null
        try {
            parsedDate = formatter.parse(value)
            println("++validated DATE TIME ++" + formatter.format(parsedDate))
        } catch (e: Exception) { //Handle exception
        }
        return parsedDate.toString()
    }

    fun capitalize(s: String): String? {
        return s.split(' ').joinToString(" ") { it.capitalize() }
    }
}