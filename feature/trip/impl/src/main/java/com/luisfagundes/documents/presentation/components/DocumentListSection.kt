package com.luisfagundes.documents.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.documents.domain.model.Attachment
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.documents.presentation.mapper.toIcon
import com.luisfagundes.documents.presentation.mapper.toTitle

@Composable
internal fun DocumentListSection(
    category: DocumentCategory,
    documents: List<Document>,
    onDocumentClick: (Document) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        DocumentListSectionHeader(
            title = category.toTitle(context).uppercase(),
            count = documents.size
        )
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            documents.forEachIndexed { index, document ->
                DocumentItem(
                    document = document,
                    onClick = { onDocumentClick(document) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.default)
                )
                if (index < documents.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default)
                    )
                }
            }
        }
    }
}

@Composable
private fun DocumentListSectionHeader(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DocumentItem(
    document: Document,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DocumentCategoryIcon(category = document.category)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.verySmall)
        ) {
            Text(
                text = document.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            if (document.description.isNotBlank()) {
                Text(
                    text = document.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        DocumentExtensionBadge(attachment = document.attachment)
    }
}

@Composable
private fun DocumentCategoryIcon(
    category: DocumentCategory,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(48.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Icon(
            imageVector = category.toIcon(),
            contentDescription = null,
            modifier = Modifier.padding(12.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun DocumentExtensionBadge(
    attachment: Attachment,
    modifier: Modifier = Modifier
) {
    val extension = attachment.displayExtension()
    if (extension.isBlank()) return

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = extension,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.small,
                vertical = MaterialTheme.spacing.verySmall
            ),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun Attachment.displayExtension(): String = when (this) {
    is Attachment.Loaded -> fileName.substringAfterLast('.', "").uppercase()
    is Attachment.Pending -> uri.lastPathSegment?.substringAfterLast('.', "")?.uppercase() ?: ""
}
