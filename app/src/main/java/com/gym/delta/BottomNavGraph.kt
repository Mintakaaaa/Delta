package com.gym.delta

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gym.delta.screens.HomeScreen
import com.gym.delta.screens.JourneyScreen
import com.gym.delta.screens.WeightScreen
import com.gym.delta.screens.WorkoutsScreen


/**
 * Graph of all navigation bar destinations.
 *
 * @param navController navigation bar controller
 */
@Composable
fun BottomNavGraph(navController : NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarView.Home.route
    ) {

        composable(route = BottomBarView.Home.route) { HomeScreen() }
        composable(route = BottomBarView.Workouts.route) { WorkoutsScreen() }
        composable(route = BottomBarView.Weight.route) { WeightScreen() }
        composable(route = BottomBarView.Journey.route) { JourneyScreen() }
    }
}