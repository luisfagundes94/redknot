package com.luisfagundes.itinerary.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.luisfagundes.designsystem.theme.spacing
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.presentation.factory.ItineraryItemCardFactory
import com.luisfagundes.itinerary.presentation.mapper.toIcon

@Composable
internal fun ItineraryTimeline(
    item: ItineraryItem,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    val lineColor = MaterialTheme.colorScheme.outlineVariant
    val iconSize = 40.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .width(iconSize)
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val iconCenterY = iconSize.toPx() / 2

                // Determine the start and end Y coordinates
                val startY = if (isFirst) iconCenterY else 0f
                val endY = if (isLast) iconCenterY else size.height

                drawLine(
                    color = lineColor,
                    start = Offset(centerX, startY),
                    end = Offset(centerX, endY),
                    strokeWidth = 2.dp.toPx()
                )
            }

            // The Icon sits on top of the line
            ItineraryItemTypeIcon(
                icon = item.toIcon(),
                modifier = Modifier.size(iconSize)
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.spacing.default))

        ItineraryItemCardFactory(
            item = item,
            modifier = Modifier.padding(bottom = 16.dp) // Add spacing between cards here
        )
    }
}

@Composable
private fun ItineraryItemTypeIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(20.dp)
        )
    }
}
