package com.luisfagundes.core.common.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlin.text.clear

class Navigator(val state: NavigationState) {
    fun navigateTo(route: NavKey) {
        when (route) {
            state.currentTopLevelRoute -> clearSubStack()
            in state.topLevelRoutes -> goToTopLevel(route)
            else -> goToRoute(route)
        }
    }

    fun goBack(steps: Int = 1) {
        require(steps >= 1) { "Steps must be at least 1" }

        repeat(steps) {
            when (state.currentRoute) {
                state.startRoute -> return
                state.currentTopLevelRoute -> state.topLevelStack.removeLastOrNull()
                else -> state.currentSubStack.removeLastOrNull()
            }
        }
    }

    private fun goToRoute(route: NavKey) {
        state.currentSubStack.apply {
            remove(route)
            add(route)
        }
    }

    private fun goToTopLevel(route: NavKey) {
        state.topLevelStack.apply {
            if (route == state.startRoute) clear() else remove(route)
            add(route)
        }
    }

    private fun clearSubStack() {
        state.currentSubStack.run {
            if (size > 1) subList(1, size).clear()
        }
    }
}