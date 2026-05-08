package com.luisfagundes.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import com.luisfagundes.designsystem.theme.spacing

@Composable
fun RedknotEmptyTemplate(
    title: String,
    primaryButtonLabel: String,
    onPrimaryButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    lottieAnimationResId: Int? = null,
    primaryButtonIcon: ImageVector? = null,
    primaryButtonIconDescription: String? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        lottieAnimationResId?.let {
            LottieAnimationLoader(
                animationResId = lottieAnimationResId
            )
            Spacer(
                modifier = Modifier.height(MaterialTheme.spacing.default)
            )
        }
        Text(
            text = title,
            textAlign = TextAlign.Center,
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.default)
        )
        Button(
            onClick = onPrimaryButtonClick,
        ) {
           primaryButtonIcon?.let {
               Icon(
                   painter = rememberVectorPainter(it),
                   contentDescription = primaryButtonIconDescription
               )
               Spacer(
                   modifier = Modifier.width(MaterialTheme.spacing.small)
               )
           }
            Text(
                text = primaryButtonLabel
            )
        }
    }
}