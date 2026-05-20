package com.luisfagundes.documents.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal data class DocumentsRoute(val tripId: Int) : NavKey

@Serializable
internal data class AddDocumentFormRoute(val tripId: Int) : NavKey
