package com.luisfagundes.trip.tools.extensions

import java.util.Locale

fun String.capitalizeEveryWord(): String {
    return split(" ").joinToString(" ") { word ->
        word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        }
    }
}

fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar { it.titlecase(Locale.ROOT) }
}