package com.gym.delta

import androidx.annotation.WorkerThread
import com.gym.delta.model.Workout
import kotlinx.coroutines.flow.Flow


class WorkoutRepository(private val workoutDao: WorkoutDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWorkouts: Flow<List<Workout>> = workoutDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }

    @WorkerThread
    suspend fun delete(workout: Workout) {
        workoutDao.delete(workout)
    }
}