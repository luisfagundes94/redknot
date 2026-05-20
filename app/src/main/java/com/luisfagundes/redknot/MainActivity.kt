package com.luisfagundes.redknot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.luisfagundes.designsystem.theme.RedknotTheme
import com.luisfagundes.redknot.navigation.AppNavDisplay
import com.luisfagundes.redknot.navigation.TopLevelDestinations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedknotTheme {
                RedknotApp(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun RedknotApp(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(TopLevelDestinations.TRIP_LIST.route)

    Scaffold(modifier) { innerPadding ->
        AppNavDisplay(
            backStack = backStack,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}
