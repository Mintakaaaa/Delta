package com.gym.delta

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarView(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarView(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Workouts : BottomBarView(
        route = "workouts",
        title = "Workouts",
        icon = Icons.Default.Settings
    )
    object Weight : BottomBarView(
        route = "weight",
        title = "Weight",
        icon = Icons.Default.Build
    )
    object Journey : BottomBarView(
        route = "journey",
        title = "Journey",
        icon = Icons.Default.Call
    )
}