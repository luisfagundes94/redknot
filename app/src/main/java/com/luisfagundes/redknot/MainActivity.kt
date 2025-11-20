package com.luisfagundes.redknot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.luisfagundes.redknot.navigation.AppNavigation
import com.luisfagundes.redknot.navigation.TopLevelDestinations
import com.luisfagundes.redknot.ui.theme.RedknotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedknotTheme {
                RedknotApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun RedknotApp() {
    var currentDestination by rememberSaveable {
        mutableStateOf(TopLevelDestinations.ITINERARY)
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavigation(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}