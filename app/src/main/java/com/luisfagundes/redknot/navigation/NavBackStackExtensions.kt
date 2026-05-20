package com.luisfagundes.redknot.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlin.reflect.KClass

fun NavBackStack<NavKey>.navigateTo(route: NavKey) {
    remove(route)
    add(route)
}

fun NavBackStack<NavKey>.goBack() {
    if (size > 1) removeLastOrNull()
}

fun NavBackStack<NavKey>.popBackTo(targetType: KClass<out NavKey>) {
    val index = indexOfLast { targetType.isInstance(it) }
    if (index in 0..< lastIndex) {
        subList(index + 1, size).clear()
    }
}
