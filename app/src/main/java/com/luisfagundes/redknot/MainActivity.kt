package com.luisfagundes.redknot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.luisfagundes.designsystem.theme.RedknotTheme
import com.luisfagundes.redknot.navigation.AppNavDisplay
import com.luisfagundes.redknot.navigation.Navigator
import com.luisfagundes.redknot.navigation.TopLevelDestinations
import com.luisfagundes.redknot.navigation.rememberNavigationState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

@OptIn(ExperimentalMaterial3Api::class)
@PreviewScreenSizes
@Composable
fun RedknotApp() {
    val navigationState = rememberNavigationState(
        startRoute = TopLevelDestinations.TRIP_LIST.route,
        topLevelRoutes = TopLevelDestinations.entries.map { it.route }.toSet()
    )
    val navigator = remember { Navigator(navigationState) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (navigationState.isCurrentRouteTopLevel.not()) return@Scaffold

            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                TopLevelDestinations.entries.forEach { entry ->
                    NavigationBarItem(
                        label = { Text(entry.label) },
                        icon = {
                            Icon(
                                imageVector = entry.icon,
                                contentDescription = entry.label
                            )
                        },
                        selected = entry.route == navigationState.topLevelRoute,
                        onClick = { navigationState.topLevelRoute = entry.route },
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavDisplay(
            navigationState = navigationState,
            navigator = navigator,
            modifier = Modifier.padding(innerPadding)
        )
    }
}