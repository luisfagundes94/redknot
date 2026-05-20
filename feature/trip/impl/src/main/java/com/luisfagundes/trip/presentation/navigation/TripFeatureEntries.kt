package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.documents.presentation.navigation.documentsEntries
import com.luisfagundes.itinerary.presentation.navigation.itineraryEntries
import kotlin.reflect.KClass

fun EntryProviderScope<NavKey>.tripFeatureEntries(
    navigateTo: (NavKey) -> Unit,
    goBack: () -> Unit,
    popBackTo: (KClass<out NavKey>) -> Unit,
) {
    tripEntries(navigateTo, goBack)
    itineraryEntries(navigateTo, goBack, popBackTo)
    documentsEntries(navigateTo, goBack)
}
