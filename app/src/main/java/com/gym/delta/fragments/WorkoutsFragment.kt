package com.gym.delta.fragments

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi


data class Workout(
    val name: String,
    val days: List<Boolean>
)

/**
 * Workouts view;
 * Contains a heading and workouts container underneath;
 */
@Composable
fun WorkoutsFragment() {
    var workouts = arrayOf(
        Workout("One", listOf(true, true, true, false, false, true, false)),
        Workout("Two", listOf(true, false, false, false, false, false, true)),
        Workout("Three", listOf(false, false, true, true, false, false, false))
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 80.dp),
        contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "What are we\ndoing today?",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(36.dp))
            WorkoutsContainer(workouts)
        }
    }
}

/**
 * Workouts container;
 * Contains the container title and the content container
 */
@Composable
fun WorkoutsContainer(workouts : Array<Workout>) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopRoundedTitleContainer("Workouts")
            TopRoundedContentContainer(workouts)
        }
    }
}

/**
 * Title container for content container
 * @param title title of the contained content
 */
@Composable
fun TopRoundedTitleContainer(title : String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.Gray)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title)
        }
    }
}

/**
 * Content container
 */
@Composable
fun TopRoundedContentContainer(workouts : Array<Workout>) {
    var workout = Workout("New Workout", listOf(true, true, false, false, false, true, false))
    var workoutTwo = Workout("Newer Workout", listOf(false, false, false, false, false, true, false))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.Gray)
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            WorkoutElement(workout = workout)
            WorkoutElement(workout = workoutTwo)
        }
    }
}


@OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun WorkoutElement(workout : Workout) {
    val checkboxStates = remember { workout.days.toMutableStateList() } // LOAD WORKOUT DAYS INTO MUTABLE LIST
    Log.d("states0", checkboxStates[0].toString())
    Log.d("workout.days = ", workout.days.toString())

    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(10.dp)
        ) {
            Text(text = workout.name)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "^")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
    ) {
        Row( // DAYS & SELECT
            Modifier.padding(10.dp)
        ) {
            Text(text = "Days", color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "SELECT", color = Color.White)
        }
        Row( // CHECKBOXES
            Modifier.padding(10.dp)
        ) {
            checkboxStates.forEachIndexed { index, dayChecked ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text( text = days[index], color = Color.LightGray ) // MON TUE WED...
                    Checkbox(
                        checked = dayChecked,
                        onCheckedChange = { isChecked ->
                            checkboxStates[index] = isChecked
                            Log.d("state $index", checkboxStates[index].toString())
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Green,
                            uncheckedColor = Color.Red,
                            checkmarkColor = Color.White
                        ),
                    )
                }
            }
        }
        Text(
            text = "Every Wednesday", Modifier.padding(10.dp)
        )
    }
}

//fun getSelectedDaysText(days : Array<Boolean>): String {
//    var daysText = "Every "
//    if (days[0]) {
//        daysText += "Monday"
//    }
//    return daysText
//}

@Composable
fun WorkoutCheckboxesContainer() {
//    val checkboxStates = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(10.dp),
    ) {
        Row() {
            Text(text = "New Workout")
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "^")
        }
    }
}

//@Composable
//fun DayElement(day : String, isChecked : Boolean) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = day,
//        )
//        Checkbox(
//            checked = isChecked,
//            onCheckedChange = { isChecked = it },
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Green,
//                uncheckedColor = Color.Red,
//                checkmarkColor = Color.White
//            )
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun WorkoutsPreview() {
    WorkoutsFragment()
}