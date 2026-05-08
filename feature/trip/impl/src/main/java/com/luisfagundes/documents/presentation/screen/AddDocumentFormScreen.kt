package com.luisfagundes.documents.presentation.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.documents.presentation.components.AttachmentChip
import com.luisfagundes.documents.presentation.components.AttachmentSection
import com.luisfagundes.documents.presentation.components.DocumentCategorySection
import com.luisfagundes.documents.presentation.extensions.displayName
import com.luisfagundes.documents.presentation.extensions.displaySize
import com.luisfagundes.documents.presentation.extensions.createImageUri
import com.luisfagundes.documents.presentation.viewmodel.AddDocumentFormViewModel
import com.luisfagundes.documents.presentation.viewmodel.effect.AddDocumentFormUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.AddDocumentFormUiState
import com.luisfagundes.trip.R

@Composable
internal fun AddDocumentFormScreen(
    tripId: Int,
    onNavigateBack: () -> Unit,
    viewModel: AddDocumentFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var pendingPhotoUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        viewModel.initForm(tripId)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingPhotoUri?.let { viewModel.onPhotoTaken(it) }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createImageUri(context)
            pendingPhotoUri = uri
            cameraLauncher.launch(uri)
        }
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is AddDocumentFormUiEffect.NavigateBack -> onNavigateBack()
            is AddDocumentFormUiEffect.LaunchCamera -> {
                val hasCameraPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
                if (hasCameraPermission) {
                    val uri = createImageUri(context)
                    pendingPhotoUri = uri
                    cameraLauncher.launch(uri)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
            is AddDocumentFormUiEffect.LaunchFilePicker -> Unit
        }
    }

    AddDocumentFormContent(
        uiState = uiState,
        onCategorySelect = viewModel::onCategorySelect,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onTakePhotoClick = viewModel::onTakePhotoClick,
        onUploadFileClick = viewModel::onUploadFileClick,
        onAttachmentRemove = viewModel::onAttachmentRemove,
        onSaveDocumentClick = viewModel::onSaveDocumentClick,
        onBackClick = viewModel::onBackClick
    )
}

@OptIn(ExperimentalGridApi::class)
@Composable
private fun AddDocumentFormContent(
    uiState: AddDocumentFormUiState,
    onCategorySelect: (DocumentCategory) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTakePhotoClick: () -> Unit,
    onUploadFileClick: () -> Unit,
    onAttachmentRemove: () -> Unit,
    onSaveDocumentClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.add_document_title),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.default),
                onClick = onSaveDocumentClick
            ) {
                Text(
                    text = stringResource(R.string.save_document)
                )
            }
        }
    ) { internalPadding ->
        Column(
            modifier = Modifier
                .padding(internalPadding)
                .padding(MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            DocumentCategorySection(
                selectedCategory = uiState.category,
                onCategorySelect = onCategorySelect,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = { Text(stringResource(R.string.title)) },
                placeholder = { Text(stringResource(R.string.document_placeholder_title)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                label = { Text(stringResource(R.string.description)) },
                placeholder = { Text(stringResource(R.string.document_placeholder_title)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            val attachment = uiState.attachment
            if (attachment != null) {
                AttachmentChip(
                    fileName = attachment.displayName(),
                    fileSize = attachment.displaySize(),
                    onRemoveClick = onAttachmentRemove,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                AttachmentSection(
                    onTakePhotoClick = onTakePhotoClick,
                    onUploadFileClick = onUploadFileClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
