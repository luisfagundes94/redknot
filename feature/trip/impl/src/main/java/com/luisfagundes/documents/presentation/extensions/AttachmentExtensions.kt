package com.luisfagundes.documents.presentation.extensions

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.luisfagundes.documents.domain.model.Attachment
import java.io.File

private const val BYTES_PER_MEGABYTE = 1_048_576L
private const val BYTES_PER_KILOBYTE = 1_024L

internal fun Attachment.displayName(): String = when (this) {
    is Attachment.Loaded -> fileName
    is Attachment.Pending -> uri.lastPathSegment ?: "photo.jpg"
}

internal fun List<Attachment>.displayTotalSize(): String {
    val total = this.map { attachment ->
        if (attachment is Attachment.Loaded) {
            attachment.sizeInBytes
        } else {
            0L
        }
    }
    return formatBytes(total.sum())
}

internal fun Attachment.displaySize(): String = when (this) {
    is Attachment.Loaded -> formatBytes(sizeInBytes)
    is Attachment.Pending -> {
        val file = File(uri.path ?: return "")
        if (file.exists()) formatBytes(file.length()) else ""
    }
}

internal fun createImageUri(context: Context): Uri {
    val imagesDir = File(context.cacheDir, "images").also { it.mkdirs() }
    val imageFile = File(imagesDir, "photo_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}

private fun formatBytes(bytes: Long): String {
    return when {
        bytes >= BYTES_PER_MEGABYTE -> "${bytes / BYTES_PER_MEGABYTE} MB"
        bytes >= BYTES_PER_KILOBYTE -> "${bytes / BYTES_PER_KILOBYTE} KB"
        else -> "$bytes B"
    }
}
