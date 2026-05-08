package com.luisfagundes.documents.domain.model

internal sealed interface AttachmentSource {
    data object Camera : AttachmentSource
    data object FilePicker : AttachmentSource
}