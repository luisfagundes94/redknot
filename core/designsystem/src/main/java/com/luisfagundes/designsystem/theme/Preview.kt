package com.luisfagundes.designsystem.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider

class RedknotThemeWrapper : PreviewWrapperProvider {
    @Composable
    override fun Wrap(
        content: @Composable (() -> Unit)
    ) {
        RedknotTheme {
            content()
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class DarkPreview

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class LightPreview

@DarkPreview
@LightPreview
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedknotPreview