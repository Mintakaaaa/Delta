package com.gym.delta.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Workouts view
 */
@Composable
fun WorkoutsFragment() {
    Box(modifier = Modifier.fillMaxSize().background(color = Color.LightGray).padding(bottom = 100.dp),
        contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "What are we\ndoing today?",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 36.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutsPreview() {
    WorkoutsFragment()
}