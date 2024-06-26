package com.gym.delta

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.navigation_activity)

        // set up nav-host-fragment which contains all fragments (workouts, weight, etc.)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // set up controller for navigation
        val navController = host.navController

        // set up bottom nav menu which includes all menus
        setupBottomNavMenu(navController)

        // set up listener for when i go to different activity
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Toast.makeText(this@MainActivity, "Navigated to $dest", Toast.LENGTH_SHORT).show()
            android.util.Log.d("NavigationActivity", "Navigated to $dest")
        }


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

//        setContent {
//            App()
//        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        // TODO STEP 9.3 - Use NavigationUI to set up Bottom Nav
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
        // TODO END STEP 9.3
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
 * Show preview of MainActivity.kt
 */
@Preview(showBackground = true)
@Composable
fun Preview() {
    App()
}