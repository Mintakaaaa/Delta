package com.gym.delta.ui.screens

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp

import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults

import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import com.gym.delta.WorkoutViewModel

import com.gym.delta.model.Workout

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.media3.common.util.UnstableApi
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
        MainContainer(workoutsState, workoutViewModel)
    }
}


/**
 * Workouts container;
 * Contains the container title and the content container
 */
@Composable
fun MainContainer(workouts: State<List<Workout>>, workoutViewModel: WorkoutViewModel) {
    val currentDay = getCurrentDayString()

    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        TopRoundedTitleContainer("Workouts", currentDay)
        Spacer(Modifier.padding(4.dp))
        WorkoutsContainer(workouts, workoutViewModel)
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
            modifier = Modifier
                .weight(1f)
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
fun WorkoutsContainer(workouts : State<List<Workout>>, workoutViewModel: WorkoutViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(workouts.value, key = { workout -> workout.id }) { workout ->
//            var visible by remember { mutableStateOf(true) }
//            AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
                WorkoutElement(
                    workout = workout,
                    onDeleteClick = {
//                    visible = false
                    workoutViewModel.delete(workout)
                },
                    onUpdateNameClick = { newWorkoutName ->
                    workoutViewModel.updateName(workout.name, newWorkoutName)
                })
                Spacer(Modifier.padding(4.dp))
//            }
        }
        item(key = "AddWorkoutButton") {
            Spacer(Modifier.height(10.dp))

            FilledIconButton(
                onClick = {
                    val newWorkout = Workout(name = "New Workout", days = arrayListOf(false, false, false, false, false, false, false))
                    workoutViewModel.insert(newWorkout)
                },
                modifier = Modifier
                    .size(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add button",
                    tint = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }
    }
}




@OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun WorkoutElement(workout : Workout, onDeleteClick: () -> Unit, onUpdateNameClick: (String) -> Unit) {
    val checkboxStates = remember(workout.id) { workout.days.toMutableStateList() } // Load workout days into a mutable list
    var expanded by remember(workout.id) { mutableStateOf(false) } // Remember expanded state per workout

    var editingName by remember { mutableStateOf(false) }
    var workoutName by remember { mutableStateOf(workout.name) }
    var workoutNameTextState by remember { mutableStateOf(TextFieldValue(workout.name, selection = TextRange(workout.name.length))) }
    val focusManager = LocalFocusManager.current // NOTE (later use?) focusManager.clearFocus()
    val focusRequester = remember { FocusRequester() }

    Row(modifier = Modifier.padding(bottom = 5.dp), horizontalArrangement = Arrangement.Start){
        Text(
            text = getDaysSelectedText(checkboxStates),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(0.dp)).clickable {
                Log.d("Workout Element", "Clicked element, not necessarily ^")
                // TODO from here, enter this specific workout page... where you can see workout notes, exercises, etc. refer to figma.
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp).fillMaxWidth()
        ) {
            AnimatedVisibility(
                visible = expanded && !editingName,
                enter = expandHorizontally(expandFrom = Alignment.Start, animationSpec = tween(delayMillis = 500)) ,
                exit = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut(),
            ) {
                Row {
                    FilledIconButton(
                        onClick = {
                            editingName = true
                            expanded = false
                        },
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit button",
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                }
            }
            AnimatedVisibility(
                visible = editingName,
                enter = expandHorizontally(animationSpec = tween(durationMillis = 500, delayMillis = 500)) + fadeIn(),
                exit = shrinkHorizontally(animationSpec = tween(durationMillis = 500)) + fadeOut(),

                ) {
                Column {
                    BasicTextField(
                        value = workoutNameTextState,
                        onValueChange = { newText -> workoutNameTextState = newText },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary), // cursor color
                        textStyle =  TextStyle(fontFamily = kanitFontFamily, fontSize = 22.sp, color = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .onGloballyPositioned { focusRequester.requestFocus() },
                        // NOTE: below is optional UNDERLINE
                        //                            decorationBox = { innerTextField ->
                        //                                Column(modifier = Modifier.padding(end = 5.dp)) {
                        //                                    innerTextField()
                        //                                    Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
                        //                                }
                        //                            }
                    )

                    Text(
                        text = "Editing workout name",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(0.dp)
                    )

                }
                Spacer(Modifier.weight(1f))
            }

            AnimatedVisibility(visible = !editingName,
                enter = expandHorizontally(expandFrom = Alignment.Start, animationSpec = tween(durationMillis = 500, delayMillis = 500)) + fadeIn(),
                exit = shrinkHorizontally(shrinkTowards = Alignment.Start, animationSpec = tween(durationMillis = 500, delayMillis = 0)) + fadeOut(),
                ) {
                Text(
                    text = workoutName, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            
            AnimatedVisibility(
                visible = editingName,
                enter = expandHorizontally(animationSpec = tween(durationMillis = 500, delayMillis = 500)) + fadeIn(),
                exit = shrinkHorizontally(animationSpec = tween(durationMillis = 500)) + fadeOut(),

            ) {
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { /* TODO TICK FUNCTIONALITY */
                            val newWorkoutName = workoutNameTextState.text
                            onUpdateNameClick(newWorkoutName)
                            workoutName = newWorkoutName
                            editingName = false
                            expanded = true
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = "Check", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxSize())
                    }

                    IconButton(
                        onClick = { // exit editingName state, enter expanded state, and reset workout name text field
                            workoutNameTextState = TextFieldValue(text = workoutName, selection = TextRange(workoutName.length))
                            editingName = false
                            expanded = true
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxSize())
                    }
                }
            }
            AnimatedVisibility(
                visible = !editingName,
                enter = expandHorizontally(animationSpec = tween(durationMillis = 500, delayMillis = 500)) + fadeIn(),
                exit = shrinkHorizontally(animationSpec = tween(durationMillis = 500)) + fadeOut(),
            ) {
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(40.dp)
                ) {
                    if (expanded) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Arrow up", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxSize()) }
                    else { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Arrow down", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxSize()) }
                }
            }
        }


        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            ExpandableDaysCard(checkboxStates, onDeleteClick = onDeleteClick)
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun ExpandableDaysCard(checkboxStates: SnapshotStateList<Boolean>?, onDeleteClick: () -> Unit) {
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
                            // TODO on check change update days of this workout!!
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            FilledIconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .size(40.dp),
                shape = RoundedCornerShape(10.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Add button",
                    tint = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }
    }
}

val dayOfWeekMap = mapOf(
    0 to "Monday",
    1 to "Tuesday",
    2 to "Wednesday",
    3 to "Thursday",
    4 to "Friday",
    5 to "Saturday",
    6 to "Sunday"
)

fun getDaysSelectedText(days: SnapshotStateList<Boolean>?): String {
    var daysText = ""
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
        if (days != null) {
            days.forEachIndexed() { dayIndex, isChecked ->
                if (isChecked) {
                    daysText += ( dayOfWeekMap[dayIndex] + " | ")
                }
            }
            daysText = daysText.substring(0, daysText.length - 3)
        }
    }
    return daysText
}

@Preview
@Composable
fun WorkoutsPreview() {

    DeltaTheme(darkTheme = true) {

//        val viewModel = WorkoutViewModel(WorkoutRepository())
//        val viewModel = WorkoutViewModel(WorkoutRepository(AppDatabase.getInstance(LocalContext.current, CoroutineScope(Dispatchers.IO)).workoutDao()))
//        WorkoutsScreen(workoutViewModel = viewModel)
        WorkoutElement(Workout(id = 0, name = "Hello", days = arrayListOf(true, true, true, false, false, true, true)), onDeleteClick = {}, onUpdateNameClick = {})
//        TopRoundedTitleContainer("Workouts", "Saturday")
//        ExpandableDaysCard(checkboxStates = null)

    }
}