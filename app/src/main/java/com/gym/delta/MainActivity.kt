package com.gym.delta

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gym.delta.ui.components.DeltaTabRow
import com.gym.delta.ui.theme.DeltaTheme

class MainActivity : ComponentActivity() {
    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

//        val db = AppDatabase.getInstance(this)
//
//        AppDatabase.populateWithDummy(db) { success ->
//            if (success) {
//                Log.d("DB", "POPULATED")
//            }
//            else {
//                Log.d("DB", "NOT populated")
//            }
//        }
//        AppDatabase.getWorkoutsFromDb(db) { workouts ->
//            // update ui, you have workouts now
//        }

        setContent {
            DeltaTheme {
                App()
            }
        }
    }
}


/**
 * Main activity is generated here
 */
@OptIn(UnstableApi::class)
@Composable
fun App() {
    DeltaTheme(darkTheme = true) {
        val navController = rememberNavController()

        val currentBackStack by navController.currentBackStackEntryAsState()
        // the line above subscribes to controller's back stack
        // this causes recomposition of composable (App and children)

        val currentDestination = currentBackStack?.destination
        val currentScreen =
            deltaTabRowScreens.find { it.route == currentDestination?.route } ?: Home
        Log.d("App()", "CURRENT SCREEN = $currentScreen")

        Scaffold(
            bottomBar = {
                DeltaTabRow(
                    allScreens = deltaTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            DeltaNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

/**
 * Show preview of MainActivity.kt
 */
@Preview
@Composable
fun Preview() {
    DeltaTheme(darkTheme = true) {
        App()
    }
}