package com.gym.delta

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.gym.delta.model.Workout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainView : ComponentActivity() {
    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // TODO make singleton for DB
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_db"
        ).build()

        // TODO make this more abstracted (i.e. make function for coroutine?)
        CoroutineScope(Dispatchers.IO).launch { // Must use coroutine (BG thread for DB access)...
            val workoutDao = db.workoutDao()
            val workouts = workoutDao.getAll()
            Log.d("Workouts from DB: ", workouts.toString())


            withContext(Dispatchers.Main) { // If you need to update the UI with the result, switch to the Main thread
                // Update UI with the workouts data
                // Example: updateUI(workouts)
                Log.d("Workouts from DB: ", workouts.toString())
            }
        }
        setContent {
            App()
        }
    }
}


/**
 * Main activity is generated here
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}

/**
 * Element containing bottom nav bar; adds bar items into bar
 *
 * @param navController navigation bar controller
 */
@Composable
fun BottomBar(navController: NavHostController) {
    val views = listOf(
        BottomBarView.Home,
        BottomBarView.Workouts,
        BottomBarView.Weight,
        BottomBarView.Journey
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        views.forEach { view ->
            NavigationBarItem(
                icon = { Icon(view.icon, contentDescription = view.title) },
                label = { Text(view.title) },
                selected = currentDestination?.hierarchy?.any { it.route == view.route } == true,
                onClick = { navController.navigate(view.route) }
            )
        }
    }
}


/**
 * Show preview of MainView.kt
 */
@Preview(showBackground = true)
@Composable
fun Preview() {
    App()
}