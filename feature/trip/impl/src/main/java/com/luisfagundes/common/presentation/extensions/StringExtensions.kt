package com.luisfagundes.common.presentation.extensions

fun String?.toTitleCase(): String =
    this?.lowercase()?.replaceFirstChar { it.uppercase() }.orEmpty()
