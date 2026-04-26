package com.luisfagundes.trip.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PhotoUrlsResponse(
    @SerialName("raw")
    val raw: String,
    @SerialName("full")
    val full: String,
    @SerialName("regular")
    val regular: String,
    @SerialName("small")
    val small: String,
    @SerialName("thumb")
    val thumb: String
)