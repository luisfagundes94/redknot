package com.luisfagundes.trip.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.documents.presentation.navigation.documentsEntries
import com.luisfagundes.itinerary.presentation.navigation.itineraryEntries

fun EntryProviderScope<NavKey>.tripFeatureEntries(
    navigateTo: (NavKey) -> Unit,
    goBack: (Int) -> Unit,
) {
    tripEntries(navigateTo, goBack)
    itineraryEntries(navigateTo, goBack)
    documentsEntries(navigateTo, goBack)
}
