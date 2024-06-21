package com.gym.delta

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gym.delta.fragments.HomeFragment
import com.gym.delta.fragments.WorkoutsFragment
import com.gym.delta.fragments.WeightFragment
import com.gym.delta.fragments.JourneyFragment

@Composable
fun BottomNavGraph(navController : NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarView.Home.route
    ) {
        composable(route = BottomBarView.Home.route) { HomeFragment() }
        composable(route = BottomBarView.Workouts.route) { WorkoutsFragment() }
        composable(route = BottomBarView.Weight.route) { WeightFragment() }
        composable(route = BottomBarView.Journey.route) { JourneyFragment() }
    }
}