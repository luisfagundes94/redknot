package com.luisfagundes.trip.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PhotoResponse(
    @SerialName("description")
    val description: String?,
    @SerialName("urls")
    val urls: PhotoUrlsResponse,
)