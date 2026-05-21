package com.luisfagundes.documents.presentation.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.components.RedknotTopBar
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.documents.domain.model.Attachment
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.presentation.extensions.displayName
import com.luisfagundes.documents.presentation.extensions.displaySize
import com.luisfagundes.documents.presentation.extensions.resolveShareableUri
import com.luisfagundes.documents.presentation.viewmodel.DocumentDetailViewModel
import com.luisfagundes.documents.presentation.viewmodel.effect.DocumentDetailUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.DocumentDetailUiState
import com.luisfagundes.trip.R
import com.luisfagundes.trip.presentation.components.DeleteConfirmationDialog

@Composable
internal fun DocumentDetailScreen(
    documentId: Int,
    onNavigateBack: () -> Unit,
    viewModel: DocumentDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getDocument(documentId)
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is DocumentDetailUiEffect.NavigateBack -> onNavigateBack()
        }
    }

    when (val state = uiState) {
        is DocumentDetailUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )
        is DocumentDetailUiState.Error -> DocumentDetailErrorContent(
            message = state.message ?: stringResource(R.string.generic_error_message),
            onBackClick = onNavigateBack
        )
        is DocumentDetailUiState.Content -> DocumentDetailContent(
            document = state.document,
            onDeleteClick = viewModel::deleteDocument,
            onBackClick = onNavigateBack
        )
    }
}

@Composable
private fun DocumentDetailContent(
    document: Document,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            title = stringResource(R.string.delete_document_dialog_title),
            message = stringResource(R.string.delete_document_dialog_message),
            onDismissRequest = { showDeleteDialog = false },
            onDeleteClick = { showDeleteDialog = false; onDeleteClick() }
        )
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            RedknotTopBar(
                title = document.title,
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_document_description)
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        DocumentAttachmentViewer(
            attachment = document.attachment,
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun DocumentAttachmentViewer(
    attachment: Attachment,
    modifier: Modifier = Modifier
) {
    val uri = when (attachment) {
        is Attachment.Loaded -> attachment.uri
        is Attachment.Pending -> attachment.uri
    }
    val isImage = when (attachment) {
        is Attachment.Loaded -> attachment.mimeType.startsWith("image/")
        is Attachment.Pending -> true
    }

    if (isImage) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
        )
    } else {
        val mimeType = (attachment as? Attachment.Loaded)?.mimeType ?: "*/*"
        DocumentFileViewer(
            fileName = attachment.displayName(),
            fileSize = attachment.displaySize(),
            uri = uri,
            mimeType = mimeType,
            modifier = modifier
        )
    }
}

@Composable
private fun DocumentFileViewer(
    fileName: String,
    fileSize: String,
    uri: Uri,
    mimeType: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val noAppMessage = stringResource(R.string.no_app_to_open_file)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = MaterialTheme.spacing.default,
            alignment = Alignment.CenterVertically
        )
    ) {
        Icon(
            imageVector = Icons.Default.Description,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        if (fileName.isNotBlank()) {
            Text(
                text = fileName,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
        if (fileSize.isNotBlank()) {
            Text(
                text = fileSize,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Button(
            onClick = {
                scope.launch {
                    val shareableUri = withContext(Dispatchers.IO) {
                        resolveShareableUri(context, uri, fileName)
                    }
                    if (shareableUri != null) {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(shareableUri, mimeType)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(context, noAppMessage, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, noAppMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.show_file_btn_label))
        }
    }
}


@Composable
private fun DocumentDetailErrorContent(
    message: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(),
        topBar = {
            RedknotTopBar(onBackClick = onBackClick)
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = message)
        }
    }
}

