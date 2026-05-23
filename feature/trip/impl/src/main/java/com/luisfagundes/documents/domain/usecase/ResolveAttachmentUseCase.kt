package com.luisfagundes.documents.domain.usecase

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.luisfagundes.documents.domain.model.Attachment
import com.luisfagundes.documents.domain.model.AttachmentSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class ResolveAttachmentUseCase @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    operator fun invoke(uri: Uri, source: AttachmentSource): Attachment {
        return runCatching {
            var fileName = ""
            var sizeInBytes = 0L

            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (cursor.moveToFirst()) {
                    if (nameIndex >= 0) fileName = cursor.getString(nameIndex)
                    if (sizeIndex >= 0) sizeInBytes = cursor.getLong(sizeIndex)
                }
            }

            val mimeType = context.contentResolver.getType(uri).orEmpty()

            Attachment.Loaded(
                uri = uri,
                fileName = fileName,
                sizeInBytes = sizeInBytes,
                mimeType = mimeType,
                source = source
            )
        }.getOrElse {
            Attachment.Pending(uri = uri, source = source)
        }
    }
}
