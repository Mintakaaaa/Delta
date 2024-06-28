package com.gym.delta.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.ArrayList
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.gym.delta.R
import com.gym.delta.ui.theme.DeltaTheme


/**
 * Workouts view;
 * Contains a heading and workouts container underneath;
 */
@Composable
fun WorkoutsScreen(workoutViewModel: WorkoutViewModel) {
    val workoutsState = workoutViewModel.allWorkouts.observeAsState(initial = emptyList())

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = buildAnnotatedString {
                append("What are we\ndoing ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("today")
                }
                append("?")
            },
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 35.sp
            ),
            modifier = Modifier.padding(40.dp)
        )
        WorkoutsContainer(workoutsState)
//            Button(
//                onClick = {
//                    workoutViewModel.insert(
//                        Workout(name = "New Workout", days = arrayListOf(false, false, false, false, true, true, true)))
//                },
//                modifier = Modifier.padding(bottom = 16.dp)
//            ) {
//                Text("Add Workout")
//            }

//            workoutsState.value.forEach { workout ->
//                WorkoutItem(workout = workout)
//                Spacer(modifier = Modifier.height(8.dp))
//            }
    }
}

//@Composable
//fun WorkoutItem(workout: Workout) {
//    Column {
//        workout.name?.let { Text(text = it) }
//        Text(text = workout.days.toString())
//    }
//}


/**
 * Workouts container;
 * Contains the container title and the content container
 */
@Composable
fun WorkoutsContainer(workouts: State<List<Workout>>) {
    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        TopRoundedTitleContainer("Workouts")
        Spacer(Modifier.padding(4.dp))
        TopRoundedContentContainer(workouts)
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
            .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = MaterialTheme.colorScheme.onPrimaryContainer, style = MaterialTheme.typography.titleSmall)
    }
}

/**
 * Content container
 */
@Composable
fun TopRoundedContentContainer(workouts : State<List<Workout>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp, bottomStart = 6.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp)
    ) {
        workouts.value.forEach { workout ->
            WorkoutElement(workout = workout)
            Spacer(Modifier.padding(4.dp))
        }
        ExpandableCard("HELLO")
    }
}


@OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun WorkoutElement(workout : Workout) {
    val checkboxStates = remember { workout.days?.toMutableStateList() } // LOAD WORKOUT DAYS INTO MUTABLE LIST
    checkboxStates?.get(0)?.let { Log.d("states0", it.toString()) }
    Log.d("workout.days = ", workout.days.toString())

    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer, // automatically applies onSecondaryContainer colour for text!
        modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            workout.name?.let { Text(text = it) }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .width(60.dp)
                    .height(40.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.drop_down),
                    contentDescription = "Drop down image",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color.DarkGray)
        ) {
            if (expanded) {
                Row( // DAYS & SELECT
                    Modifier.padding(10.dp)
                ) {
                    Text(text = "Days", color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "SELECT", color = Color.White)
                }
            }
        }
    }

//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(6.dp))
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
//            checkboxStates?.forEachIndexed { index, dayChecked ->
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
}


@Composable
fun ExpandableCard(title: String) {

    var expanded by remember { mutableStateOf (false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = { expanded = !expanded }),
    ) {
        Column(
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(8.dp)
            )
            if (expanded) {
                Text(
                    text = "Content Sample for Display on Expansion of Card",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

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


@Preview
@Composable
fun WorkoutsPreview() {
    DeltaTheme(darkTheme = true) {
        val viewModel = WorkoutViewModel(WorkoutRepository(AppDatabase.getInstance(LocalContext.current, CoroutineScope(Dispatchers.IO)).workoutDao()))
        WorkoutsScreen(workoutViewModel = viewModel)
    }
}