package com.example.articlesearch.data

import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val LOCALE_DATE_FORMAT = "dd MMM yyyy."



fun String.formatDate(): String {
    val newDate = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).parse(this) ?: return ""
    val format = SimpleDateFormat(LOCALE_DATE_FORMAT, Locale.getDefault())
    return format.format(newDate)
}