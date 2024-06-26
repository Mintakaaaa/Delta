package com.gym.delta

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * app navigation destinations
 */
sealed interface DeltaDestination {
    val route: String
    val icon: ImageVector
}


data object Home : DeltaDestination {
    override val icon = Icons.Default.Home
    override val route = "home"
}

data object Workouts : DeltaDestination {
    override val icon = Icons.Default.Settings
    override val route = "workouts"
}

data object Weight : DeltaDestination {
    override val icon = Icons.Default.Email
    override val route = "weight"
}

data object Journey : DeltaDestination {
    override val icon = Icons.Default.Face
    override val route = "journey"
}

// example with args ---------------------------------------
//data object SingleAccount : DeltaDestination {
//    override val icon = Icons.Filled.Money
//    override val route = "single_account"
//    const val accountTypeArg = "account_type"
//    val routeWithArgs = "$route/{$accountTypeArg}"
//    val arguments = listOf(
//        navArgument(accountTypeArg) { type = NavType.StringType }
//    )
//    val deepLinks = listOf(
//        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
//    )
//}

// Screens to be displayed in the component "DeltaTabRow"
val deltaTabRowScreens = listOf(Home, Workouts, Weight, Journey)
