package com.luisfagundes.documents.domain.model

internal data class Document(
    val id: Int = 0,
    val tripId: Int,
    val title: String,
    val description: String,
    val category: DocumentCategory,
    val attachment: Attachment
)