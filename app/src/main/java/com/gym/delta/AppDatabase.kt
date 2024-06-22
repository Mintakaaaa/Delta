package com.gym.delta

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gym.delta.model.Workout

@Database(entities = [Workout::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}