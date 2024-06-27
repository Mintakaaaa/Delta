package com.gym.delta.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.media3.common.util.Log
import com.gym.delta.WorkoutViewModel
import com.gym.delta.WorkoutViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gym.delta.AppDatabase
import com.gym.delta.WorkoutRepository
import com.gym.delta.model.Workout
import java.util.ArrayList


//data class Workout(
//    val name: String,
//    val days: List<Boolean>
//)

/**
 * Workouts view;
 * Contains a heading and workouts container underneath;
 */
@Composable
fun WorkoutsScreen(workoutViewModel: WorkoutViewModel) {
    val workoutsState = workoutViewModel.allWorkouts.observeAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "What are we\ndoing today?",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(36.dp)
            )
//            WorkoutsContainer(workoutsFromModel)
            Button(
                onClick = {
                    workoutViewModel.insert(
                        Workout(name = "New Workout", days = arrayListOf(false, false, false, false, true, true, true)))
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Add Workout")
            }

            workoutsState.value.forEach { workout ->
                WorkoutItem(workout = workout)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun WorkoutItem(workout: Workout) {
    Column {
        workout.name?.let { Text(text = it) }
        Text(text = workout.days.toString())
    }
}


/**
 * Workouts container;
 * Contains the container title and the content container
 */
//@Composable
//fun WorkoutsContainer(workouts: LiveData<List<com.gym.delta.model.Workout>>) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            TopRoundedTitleContainer("Workouts")
//            TopRoundedContentContainer(workouts)
//        }
//    }
//}

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
//@Composable
//fun TopRoundedContentContainer(workouts : LiveData<List<Workout>>) {
////    var workout = Workout("New Workout", listOf(true, true, false, false, false, true, false))
////    var workoutTwo = Workout("Newer Workout", listOf(false, false, false, false, false, true, false))
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
//            .background(Color.Gray)
//            .padding(10.dp),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        Column {
//            WorkoutElement(workout = workout)
//            WorkoutElement(workout = workoutTwo)
//        }
//    }
//}
//
//
//@OptIn(androidx.media3.common.util.UnstableApi::class)
//@Composable
//fun WorkoutElement(workout : Workout) {
//    val checkboxStates = remember { workout.days.toMutableStateList() } // LOAD WORKOUT DAYS INTO MUTABLE LIST
//    Log.d("states0", checkboxStates[0].toString())
//    Log.d("workout.days = ", workout.days.toString())
//
//    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
//    Column {
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .background(Color.White)
//            .padding(10.dp)
//        ) {
//            Text(text = workout.name)
//            Spacer(modifier = Modifier.weight(1f))
//            Text(text = "^")
//        }
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .background(Color.DarkGray)
//    ) {
//        Row( // DAYS & SELECT
//            Modifier.padding(10.dp)
//        ) {
//            Text(text = "Days", color = Color.White)
//            Spacer(modifier = Modifier.weight(1f))
//            Text(text = "SELECT", color = Color.White)
//        }
//        Row( // CHECKBOXES
//            Modifier.padding(10.dp)
//        ) {
//            checkboxStates.forEachIndexed { index, dayChecked ->
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text( text = days[index], color = Color.LightGray ) // MON TUE WED...
//                    Checkbox(
//                        checked = dayChecked,
//                        onCheckedChange = { isChecked ->
//                            checkboxStates[index] = isChecked
//                            Log.d("state $index", checkboxStates[index].toString())
//                        },
//                        colors = CheckboxDefaults.colors(
//                            checkedColor = Color.Green,
//                            uncheckedColor = Color.Red,
//                            checkmarkColor = Color.White
//                        ),
//                    )
//                }
//            }
//        }
//        Text(
//            text = getSelectedDaysText(checkboxStates), Modifier.padding(10.dp)
//        )
//    }
//}
//
//fun getSelectedDaysText(days: SnapshotStateList<Boolean>): String {
//    var daysText = "Every "
//    var noneSelected = false
//    var selected = 0
//    for (day in days) {
//        if (day) {
//            selected ++
//        }
//    }
//    if (selected == 0) { daysText = "No days selected" }
//    else {
//        if (days[0]) {
//            daysText += "Monday, "
//        }
//        if (days[1]) {
//            daysText += "Tuesday, "
//        }
//        if (days[2]) {
//            daysText += "Wednesday, "
//        }
//        if (days[3]) {
//            daysText += "Thursday, "
//        }
//        if (days[4]) {
//            daysText += "Friday, "
//        }
//        if (days[5]) {
//            daysText += "Saturday, "
//        }
//        if (days[6]) {
//            daysText += "Sunday"
//        }
//    }
//    return daysText
//}
//
//@Composable
//fun WorkoutCheckboxesContainer() {
////    val checkboxStates = remember { mutableStateListOf(false, false, false, false, false, false, false) }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .background(Color.White)
//            .padding(10.dp),
//    ) {
//        Row() {
//            Text(text = "New Workout")
//            Spacer(modifier = Modifier.weight(1f))
//            Text(text = "^")
//        }
//    }
//}
//
////@Composable
////fun DayElement(day : String, isChecked : Boolean) {
////    Column(
////        horizontalAlignment = Alignment.CenterHorizontally
////    ) {
////        Text(
////            text = day,
////        )
////        Checkbox(
////            checked = isChecked,
////            onCheckedChange = { isChecked = it },
////            colors = CheckboxDefaults.colors(
////                checkedColor = Color.Green,
////                uncheckedColor = Color.Red,
////                checkmarkColor = Color.White
////            )
////        )
////    }
////}


//@Preview(showBackground = true)
//@Composable
//fun WorkoutsPreview() {
//    WorkoutsScreen()
//}

@Preview(showBackground = true)
@Composable
fun WorkoutsPreview() {
    // Provide a mock ViewModel for preview

    val viewModel = WorkoutViewModel(WorkoutRepository(AppDatabase.getInstance(LocalContext.current).workoutDao()))
    WorkoutsScreen(workoutViewModel = viewModel)
}

//// Mock repository and data for preview purposes
//class WorkoutRepository {
//    private val mockWorkouts = listOf(
//        Workout("Running", "Run for 30 minutes"),
//        Workout("Yoga", "Practice yoga for flexibility"),
//        Workout("Weightlifting", "Lift weights for strength")
//    )
//
//    fun getAllWorkouts() = mockWorkouts
//}