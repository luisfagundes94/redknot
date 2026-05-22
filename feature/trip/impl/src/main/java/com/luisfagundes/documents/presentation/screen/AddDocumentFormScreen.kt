package com.luisfagundes.documents.presentation.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.luisfagundes.documents.presentation.tools.rememberDocumentAttachmentLaunchers
import com.luisfagundes.documents.presentation.viewmodel.AddDocumentFormViewModel
import com.luisfagundes.documents.presentation.viewmodel.effect.AddDocumentFormUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.AddDocumentFormUiState
import com.luisfagundes.trip.R

import com.luisfagundes.documents.presentation.viewmodel.event.AddDocumentFormUiEvent

@Composable
internal fun AddDocumentFormScreen(
    tripId: Int,
    onNavigateBack: () -> Unit,
    viewModel: AddDocumentFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(AddDocumentFormUiEvent.InitForm(tripId))
    }

    val attachmentLaunchers = rememberDocumentAttachmentLaunchers(
        onPhotoTaken = { uri ->
            viewModel.dispatchEvent(AddDocumentFormUiEvent.HandlePhoto(uri))
        },
        onFilePicked = { uri ->
            viewModel.dispatchEvent(AddDocumentFormUiEvent.HandleFile(uri))
        },
    )

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is AddDocumentFormUiEffect.NavigateBack -> onNavigateBack()
            is AddDocumentFormUiEffect.LaunchCamera -> attachmentLaunchers.launchCamera()
            is AddDocumentFormUiEffect.LaunchFilePicker -> attachmentLaunchers.launchFilePicker()
        }
    }

    AddDocumentFormContent(
        uiState = uiState,
        onEvent = viewModel::dispatchEvent
    )
}

@OptIn(ExperimentalGridApi::class)
@Composable
private fun AddDocumentFormContent(
    uiState: AddDocumentFormUiState,
    onEvent: (AddDocumentFormUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RedknotTopBar(
                title = stringResource(R.string.add_document_title),
                onBackClick = { onEvent(AddDocumentFormUiEvent.NavigateBack) }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.default),
                onClick = { onEvent(AddDocumentFormUiEvent.SaveDocument) }
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
                onCategorySelect = { category ->
                    onEvent(AddDocumentFormUiEvent.UpdateCategory(category))
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { title ->
                    onEvent(AddDocumentFormUiEvent.UpdateTitle(title))
                },
                label = { Text(stringResource(R.string.title)) },
                placeholder = { Text(stringResource(R.string.document_placeholder_title)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { description ->
                    onEvent(AddDocumentFormUiEvent.UpdateDescription(description))
                },
                label = { Text(stringResource(R.string.optional_description)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.attachment != null) {
                AttachmentChip(
                    fileName = uiState.attachment.displayName(),
                    fileSize = uiState.attachment.displaySize(),
                    onRemoveClick = { onEvent(AddDocumentFormUiEvent.RemoveAttachment) },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                AttachmentSection(
                    onTakePhotoClick = { onEvent(AddDocumentFormUiEvent.TakePhoto) },
                    onUploadFileClick = { onEvent(AddDocumentFormUiEvent.UploadFile) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
