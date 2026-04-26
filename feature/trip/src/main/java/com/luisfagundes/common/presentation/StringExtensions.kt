package com.luisfagundes.common.presentation

fun String?.toTitleCase(): String =
    this?.lowercase()?.replaceFirstChar { it.uppercase() }.orEmpty()
