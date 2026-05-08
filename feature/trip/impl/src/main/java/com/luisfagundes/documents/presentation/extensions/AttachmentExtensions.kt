package com.luisfagundes.documents.presentation.extensions

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.luisfagundes.documents.domain.model.Attachment
import java.io.File

internal fun Attachment.displayName(): String = when (this) {
    is Attachment.Loaded -> fileName
    is Attachment.Pending -> uri.lastPathSegment ?: "photo.jpg"
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
        bytes >= 1_048_576 -> "${bytes / 1_048_576} MB"
        bytes >= 1_024 -> "${bytes / 1_024} KB"
        else -> "$bytes B"
    }
}
