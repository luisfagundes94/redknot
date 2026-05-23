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

internal fun Attachment.displaySize(): String = when (this) {
    is Attachment.Loaded -> formatBytes(sizeInBytes)
    is Attachment.Pending -> {
        val file = File(uri.path ?: return "")
        if (file.exists()) formatBytes(file.length()) else ""
    }
}

internal fun createImageUri(context: Context): Uri {
    val documentsDir = File(context.cacheDir, "documents").also { it.mkdirs() }
    val imageFile = File(documentsDir, "photo_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}

internal fun resolveShareableUri(
    context: Context,
    sourceUri: Uri,
    fileName: String,
): Uri? {
    if (sourceUri.authority == "${context.packageName}.fileprovider") return sourceUri

    return runCatching {
        val cacheDir = File(context.cacheDir, "documents").also { it.mkdirs() }
        val destName = fileName.ifBlank { "document_${System.currentTimeMillis()}" }
        val destFile = File(cacheDir, destName)
        if (!destFile.exists()) {
            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                destFile.outputStream().use { output -> input.copyTo(output) }
            }
        }
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", destFile)
    }.getOrNull()
}

private fun formatBytes(bytes: Long): String {
    return when {
        bytes >= BYTES_PER_MEGABYTE -> "${bytes / BYTES_PER_MEGABYTE} MB"
        bytes >= BYTES_PER_KILOBYTE -> "${bytes / BYTES_PER_KILOBYTE} KB"
        else -> "$bytes B"
    }
}
