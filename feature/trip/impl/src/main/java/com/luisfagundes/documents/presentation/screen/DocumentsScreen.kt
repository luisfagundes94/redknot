package com.luisfagundes.documents.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.designsystem.components.RedknotLoadingTemplate
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.documents.presentation.components.DocumentListSection
import com.luisfagundes.documents.presentation.extensions.displayTotalSize
import com.luisfagundes.documents.presentation.viewmodel.DocumentsViewModel
import com.luisfagundes.documents.presentation.viewmodel.effect.DocumentsUiEffect
import com.luisfagundes.documents.presentation.viewmodel.state.DocumentsUiState
import com.luisfagundes.trip.R

@Composable
internal fun DocumentsScreen(
    tripId: Int,
    onNavigateToDocumentForm: () -> Unit,
    viewModel: DocumentsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is DocumentsUiEffect.NavigateToDocumentForm -> onNavigateToDocumentForm()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getDocuments(tripId)
    }

    when (uiState) {
        is DocumentsUiState.Loading -> RedknotLoadingTemplate(
            modifier = Modifier.fillMaxSize()
        )

        is DocumentsUiState.Empty -> DocumentEmptyContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.default),
            onAddDocumentClick = viewModel::navigateToDocumentForm
        )

        is DocumentsUiState.Content -> DocumentsContent(
            modifier = Modifier.fillMaxSize(),
            documentsByCategory = (uiState as DocumentsUiState.Content).documentsByCategory,
            onAddDocumentClick = viewModel::navigateToDocumentForm
        )
    }
}

@Composable
private fun DocumentEmptyContent(
    modifier: Modifier = Modifier,
    onAddDocumentClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = MaterialTheme.spacing.default,
            alignment = Alignment.CenterVertically
        )
    ) {
        Icon(
            modifier = Modifier.size(56.dp),
            imageVector = Icons.Default.Description,
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.no_documents_yet_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.no_documents_yet_description),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onAddDocumentClick
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.add_first_document_btn_label)
            )
        }
    }
}

@Composable
private fun DocumentsContent(
    documentsByCategory: Map<DocumentCategory, List<Document>>,
    onAddDocumentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalFilesSize = documentsByCategory.values.flatten().map { document ->
        document.attachment
    }.displayTotalSize()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDocumentClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_document_description)
                )
            }
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding),
            contentPadding = PaddingValues(MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            item {
                Text(
                    text = stringResource(R.string.documents_size, totalFilesSize),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            documentsByCategory.forEach { (category, documents) ->
                item(key = category.name) {
                    DocumentListSection(
                        category = category,
                        documents = documents,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
    }
}
