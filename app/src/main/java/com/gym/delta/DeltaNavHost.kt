package com.gym.delta

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gym.delta.ui.screens.HomeScreen
import com.gym.delta.ui.screens.JourneyScreen
import com.gym.delta.ui.screens.WeightScreen
import com.gym.delta.ui.screens.WorkoutsScreen


/**
 * Graph of all navigation bar destinations.
 *
 * @param navController navigation bar controller
 * @param modifier styling modifier
 */
@Composable
fun DeltaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen()
        }
        composable(route = Workouts.route) {
            WorkoutsScreen()
        }
        composable(route = Weight.route) {
            WeightScreen()
        }
        composable(route = Journey.route) {
            JourneyScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }


// example of something lol --------------------------------------------------
//private fun NavHostController.navigateToSingleAccount(accountType: String) {
//    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
//}
