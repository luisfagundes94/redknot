package com.luisfagundes.trip.api.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class DocumentsRoute(val tripId: Int) : NavKey

@Serializable
data class AddDocumentFormRoute(val tripId: Int) : NavKey