package com.luisfagundes.redknot.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(route: NavKey) {
    remove(route)
    add(route)
}

fun NavBackStack<NavKey>.goBack(steps: Int = 1) {
    require(steps >= 1) { "Steps must be at least 1" }
    repeat(steps) {
        if (size > 1) removeLastOrNull()
    }
}
