package com.luisfagundes.documents.presentation.tools

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.luisfagundes.documents.presentation.extensions.createImageUri

@Stable
internal class DocumentAttachmentLaunchers(
    private val onLaunchCamera: () -> Unit,
    private val onLaunchFilePicker: () -> Unit,
) {
    fun launchCamera() = onLaunchCamera()
    fun launchFilePicker() = onLaunchFilePicker()
}

@Composable
internal fun rememberDocumentAttachmentLaunchers(
    onPhotoTaken: (Uri) -> Unit,
    onFilePicked: (Uri) -> Unit,
): DocumentAttachmentLaunchers {
    val context: Context = LocalContext.current
    val pendingPhotoUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingPhotoUri.value?.let(onPhotoTaken)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createImageUri(context)
            pendingPhotoUri.value = uri
            cameraLauncher.launch(uri)
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let(onFilePicked)
    }

    return remember {
        DocumentAttachmentLaunchers(
            onLaunchCamera = {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) {
                    val uri = createImageUri(context)
                    pendingPhotoUri.value = uri
                    cameraLauncher.launch(uri)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            onLaunchFilePicker = {
                filePickerLauncher.launch(arrayOf("application/pdf", "image/*"))
            }
        )
    }
}
