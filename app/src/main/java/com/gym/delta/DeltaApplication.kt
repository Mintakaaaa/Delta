package com.gym.delta

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DeltaApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    // Using 'by lazy' so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getInstance(this, applicationScope) }
    val repository by lazy { WorkoutRepository(database.workoutDao()) }

    override fun onCreate() {
        super.onCreate()
        populateDatabase()
    }

    private fun populateDatabase() {
        applicationScope.launch {
            AppDatabase.populateWithDummy(database)
        }
    }


}