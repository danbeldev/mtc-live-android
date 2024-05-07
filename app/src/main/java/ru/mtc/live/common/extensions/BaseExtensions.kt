package ru.mtc.live.common.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDate(): Date? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return dateFormat.parse(this)
}

fun Date.toBaseFormat(): String {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(this)
}