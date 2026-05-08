package com.luisfagundes.documents.presentation.mapper

import android.content.Context
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.trip.R

internal fun DocumentCategory.toTitle(context: Context): String {
    val resId = when (this) {
        DocumentCategory.ID -> R.string.id
        DocumentCategory.HOTEL -> R.string.hotel
        DocumentCategory.FLIGHT -> R.string.flight
        DocumentCategory.TICKET -> R.string.ticket
        DocumentCategory.INSURANCE -> R.string.insurance
        DocumentCategory.CAR_RENTAL -> R.string.car_rental
        DocumentCategory.PASSPORT -> R.string.passport
        DocumentCategory.OTHER -> R.string.other
    }
    return context.getString(resId)
}