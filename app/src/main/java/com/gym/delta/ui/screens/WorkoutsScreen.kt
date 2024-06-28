package com.gym.delta.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.UnstableApi
import com.gym.delta.R
import com.gym.delta.ui.theme.DeltaTheme
import com.gym.delta.ui.theme.kanitFontFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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
        MainContainer(workoutsState, onWorkoutAdded = { newWorkout -> workoutViewModel.insert(newWorkout) })
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


/**
 * Workouts container;
 * Contains the container title and the content container
 */
@Composable
fun MainContainer(workouts: State<List<Workout>>, onWorkoutAdded: (Workout) -> Unit) {
    val currentDay = getCurrentDayString()

    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        TopRoundedTitleContainer("Workouts", currentDay)
        Spacer(Modifier.padding(4.dp))
        WorkoutsContainer(workouts, onWorkoutAdded = {
            newWorkout -> onWorkoutAdded(newWorkout)
        })
    }
}

fun getCurrentDayString() : String {
    val currentDate = LocalDate.now()
    val dayOfWeek = currentDate.format(DateTimeFormatter.ofPattern("EEEE"))
    return dayOfWeek
}

/**
 * Title container for content container
 * @param title title of the contained content
 */
@Composable
fun TopRoundedTitleContainer(title : String, subHeading : String) {

    Row(
        verticalAlignment = Alignment.Bottom
    ){
        Box(modifier = Modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(10.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleSmall)
        }
        HorizontalDivider(
            modifier = Modifier.weight(1f)
                .padding(10.dp)
                .clip(RoundedCornerShape(6.dp)),
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f, )
        )

        Text(
            text = subHeading,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium

        )
    }
}

/**
 * Content container
 */
@Composable
fun WorkoutsContainer(workouts : State<List<Workout>>, onWorkoutAdded: (Workout) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(workouts.value) { workout ->
            WorkoutElement(workout = workout)
            Spacer(Modifier.padding(4.dp))
        }
        item(key = "AddWorkoutButton") {
            Spacer(Modifier.height(10.dp))

            FilledIconButton(
                onClick = {
                    val newWorkout = Workout(name = "New Workout", days = arrayListOf(false, false, false, false, false, false, false))
                    onWorkoutAdded(newWorkout)
                },
                modifier = Modifier
                    .size(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add button",
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.fillMaxSize().padding(4.dp)

                )
            }
        }
    }
}


@OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun WorkoutElement(workout : Workout) {
    val checkboxStates = remember { workout.days?.toMutableStateList() } // LOAD WORKOUT DAYS INTO MUTABLE LIST
    checkboxStates?.get(0)?.let { Log.d("states0", it.toString()) }
    Log.d("workout.days = ", workout.days.toString())


    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(0.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp)
        ) {
            workout.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.size(40.dp)
            ) {
                if (expanded) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Arrow up", modifier = Modifier.fillMaxSize()) }
                else { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Arrow down", modifier = Modifier.fillMaxSize()) }
            }
        }
        if (expanded) { ExpandableDaysCard(checkboxStates) }
    }
}


@OptIn(UnstableApi::class)
@Composable
fun ExpandableDaysCard(checkboxStates: SnapshotStateList<Boolean>?) {
    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f, )
        )
        Row( // DAYS & SELECT
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text(
                text = "When?",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row( // CHECKBOXES
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            checkboxStates?.forEachIndexed { index, dayChecked ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text( text = days[index], color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.bodyMedium) // MON TUE WED...
                    Checkbox(
                        checked = dayChecked,
                        onCheckedChange = { isChecked ->
                            checkboxStates[index] = isChecked
                            Log.d("state $index", checkboxStates[index].toString())
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            uncheckedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }
        Text(
            text = getSelectedDaysText(checkboxStates),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary,
//            textColor = MaterialTheme.colorScheme.tertiary
        )
    }
}

fun getSelectedDaysText(days: SnapshotStateList<Boolean>?): String {
    var daysText = "Every "
    var selected = 0
    if (days != null) {
        for (day in days) {
            if (day) {
                selected ++
            }
        }
    }
    if (selected == 0) { daysText = "No days selected" }
    else {
        if (days?.get(0) == true) {
            daysText += "Monday, "
        }
        if (days?.get(1) == true) {
            daysText += "Tuesday, "
        }
        if (days?.get(2)!!) {
            daysText += "Wednesday, "
        }
        if (days[3]) {
            daysText += "Thursday, "
        }
        if (days[4]) {
            daysText += "Friday, "
        }
        if (days[5]) {
            daysText += "Saturday, "
        }
        if (days[6]) {
            daysText += "Sunday"
        }
    }
    return daysText
}
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

//        val viewModel = WorkoutViewModel(WorkoutRepository())
//        val viewModel = WorkoutViewModel(WorkoutRepository(AppDatabase.getInstance(LocalContext.current, CoroutineScope(Dispatchers.IO)).workoutDao()))
//        WorkoutsScreen(workoutViewModel = viewModel)
//        WorkoutElement(Workout(id = 0, name = "Hello", days = arrayListOf(true, true, true, false, false, true, true)))
//        TopRoundedTitleContainer("Workouts", "Saturday")


    }
}