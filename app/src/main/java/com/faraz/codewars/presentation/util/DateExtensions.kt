package com.faraz.codewars.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

private val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
private val readableFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)

fun String?.toReadableDate(): String {
    if (isNullOrBlank()) return ""
    return try {
        val date = isoFormat.parse(this) ?: return this
        readableFormat.format(date)
    } catch (_: Exception) {
        this
    }
}