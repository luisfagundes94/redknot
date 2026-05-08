package com.luisfagundes.documents.data.mapper

import android.net.Uri
import com.luisfagundes.documents.data.model.DocumentEntity
import com.luisfagundes.documents.domain.model.Attachment
import com.luisfagundes.documents.domain.model.AttachmentSource
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory
import javax.inject.Inject
import androidx.core.net.toUri

internal class DocumentMapper @Inject constructor() {

    fun mapToEntity(document: Document): DocumentEntity {
        return DocumentEntity(
            id = document.id,
            tripId = document.tripId,
            title = document.title,
            description = document.description,
            category = document.category.name,
            attachmentUri = document.attachment.uri.toString(),
            attachmentSource = document.attachment.source.toStorageKey(),
            attachmentFileName = (document.attachment as? Attachment.Loaded)?.fileName,
            attachmentSizeBytes = (document.attachment as? Attachment.Loaded)?.sizeInBytes,
            attachmentMimeType = (document.attachment as? Attachment.Loaded)?.mimeType
        )
    }

    fun mapToDomain(entity: DocumentEntity): Document {
        val uri = entity.attachmentUri.toUri()
        val source = entity.attachmentSource.toAttachmentSource()
        val attachment = if (entity.attachmentFileName != null) {
            Attachment.Loaded(
                uri = uri,
                fileName = entity.attachmentFileName,
                sizeInBytes = entity.attachmentSizeBytes ?: 0L,
                mimeType = entity.attachmentMimeType.orEmpty(),
                source = source
            )
        } else {
            Attachment.Pending(uri = uri, source = source)
        }
        return Document(
            id = entity.id,
            tripId = entity.tripId,
            title = entity.title,
            description = entity.description,
            category = DocumentCategory.valueOf(entity.category),
            attachment = attachment
        )
    }

    private fun AttachmentSource.toStorageKey() = when (this) {
        is AttachmentSource.Camera -> "CAMERA"
        is AttachmentSource.FilePicker -> "FILE_PICKER"
    }

    private fun String.toAttachmentSource() = when (this) {
        "CAMERA" -> AttachmentSource.Camera
        else -> AttachmentSource.FilePicker
    }
}

private val Attachment.uri: Uri
    get() = when (this) {
        is Attachment.Pending -> uri
        is Attachment.Loaded -> uri
    }

private val Attachment.source: AttachmentSource
    get() = when (this) {
        is Attachment.Pending -> source
        is Attachment.Loaded -> source
    }
