package com.luisfagundes.documents.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.trip.R

@Composable
internal fun AttachmentSection(
    onTakePhotoClick: () -> Unit,
    onUploadFileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.attachment),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            AttachmentCard(
                icon = Icons.Outlined.CameraAlt,
                title = stringResource(R.string.take_photo),
                subtitle = stringResource(R.string.take_photo_subtitle),
                onClick = onTakePhotoClick,
                modifier = Modifier.weight(1f)
            )
            AttachmentCard(
                icon = Icons.Outlined.Upload,
                title = stringResource(R.string.upload_file),
                subtitle = stringResource(R.string.upload_file_subtitle),
                onClick = onUploadFileClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AttachmentCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.outline
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .dashedBorder(color = borderColor, cornerRadius = 12.dp)
            .clickable(onClick = onClick)
            .padding(MaterialTheme.spacing.default),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.verySmall)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

private fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 8.dp,
    dashGap: Dp = 4.dp
) = this.drawBehind {
    val strokePx = strokeWidth.toPx()
    val dashPx = dashWidth.toPx()
    val gapPx = dashGap.toPx()
    val cornerPx = cornerRadius.toPx()
    drawRoundRect(
        color = color,
        style = Stroke(
            width = strokePx,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashPx, gapPx))
        ),
        cornerRadius = CornerRadius(cornerPx)
    )
}
