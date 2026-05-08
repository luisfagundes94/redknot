package com.luisfagundes.documents.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AirplaneTicket
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import com.luisfagundes.documents.domain.model.DocumentCategory

internal fun DocumentCategory.toIcon() = when (this) {
    DocumentCategory.ID -> Icons.Default.Person
    DocumentCategory.HOTEL -> Icons.Default.Hotel
    DocumentCategory.FLIGHT -> Icons.AutoMirrored.Filled.AirplaneTicket
    DocumentCategory.TICKET -> Icons.Default.ConfirmationNumber
    DocumentCategory.INSURANCE -> Icons.Default.Shield
    DocumentCategory.CAR_RENTAL -> Icons.Default.DirectionsCar
    DocumentCategory.PASSPORT -> Icons.Default.Badge
    DocumentCategory.OTHER -> Icons.AutoMirrored.Filled.InsertDriveFile
}