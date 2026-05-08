package com.luisfagundes.documents.domain.model

import android.net.Uri

internal sealed interface Attachment {
    data class Pending(
        val uri: Uri,
        val source: AttachmentSource
    ) : Attachment

    data class Loaded(
        val uri: Uri,
        val fileName: String,
        val sizeInBytes: Long,
        val mimeType: String,
        val source: AttachmentSource
    ) : Attachment
}