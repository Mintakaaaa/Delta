package com.gym.delta.ui.components

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.gym.delta.DeltaDestination
import com.gym.delta.Home
import com.gym.delta.Journey
import com.gym.delta.Weight
import com.gym.delta.Workouts
import com.gym.delta.ui.theme.DeltaTheme

/** The row containing all menu tabs
 * @param allScreens every screen you can navigate to using bottom nav bar
 * @param onTabSelected callback function; when tab clicked, send new screen to MainActivity
 * @param currentScreen the current screen the user is looking at
 */
@Composable
fun DeltaTabRow(
    allScreens: List<DeltaDestination>,
    onTabSelected: (DeltaDestination) -> Unit,
    currentScreen: DeltaDestination
) {
    Surface(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(Color(0xff111111))
    ) {
        Row(Modifier.selectableGroup()) {
            allScreens.forEach { screen ->
                DeltaTab(
                    text = screen.route,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}


/** A single bottom nav bar menu item
 * @param text menu name
 * @param icon menu icon
 * @param onSelected callback function; on click of tab, feed back to DeltaTabRow
 * @param selected is this menu selected or not
 */
@OptIn(UnstableApi::class)
@Composable
private fun DeltaTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onSelected, // callback to DeltaTabRow
                role = Role.Tab
            )
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = Color.Red)
        if (selected) { // if this menu selected, show menu text
            Spacer(Modifier.width(12.dp))
            Text(text.uppercase())
        }
    }
}


@Preview
@Composable
fun DeltaTabRowPreview() {
    DeltaTheme(true) {
        val mockScreens = listOf(Home, Workouts, Weight, Journey)

        DeltaTabRow(
            allScreens = mockScreens,
            onTabSelected = { /* Do nothing for preview */ },
            currentScreen = mockScreens.first()
        )
    }

}