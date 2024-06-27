package com.gym.delta

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gym.delta.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.gym.delta.model.Workout
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

@Database(entities = [Workout::class], version = 2)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    private class AppDatabaseCallback : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    var workoutDao = database.workoutDao()

                    // Delete all content here.
                    workoutDao.deleteAll()

                    // Add workouts as needed
                    var w = Workout(name = "Workout A", days = arrayListOf(true, false, true, false, true, false, true)) // y f y f y f y
                    var ww = Workout(name = "Workout B", days = arrayListOf(false, true, false, true, false, true, false)) // f y f y f y f
                    var www = Workout(name = "Workout C", days = arrayListOf(true, true, true, true, true, true, true)) // y y y y y y y
                    workoutDao.insertAll(w, ww, www)
                }
            }
        }
    }

    companion object {
        private const val DATABASE_NAME = "delta_db"

        /**
         * As we need only one instance of db in our app will use to store
         * This is to avoid memory leaks in android when there exist multiple instances of db
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
//                        .addMigrations(MIGRATION_1_2)
                        .addCallback(AppDatabaseCallback())
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

        fun populateWithDummy(db: AppDatabase, onResult: (Boolean) -> Unit) {
            try {
                CoroutineScope(Dispatchers.IO).launch {

                    val w = Workout(name = "Workout A", days = arrayListOf(true, false, true, false, true, false, true)) // y f y f y f y
                    val ww = Workout(name = "Workout B", days = arrayListOf(false, true, false, true, false, true, false)) // f y f y f y f
                    val www = Workout(name = "Workout C", days = arrayListOf(true, true, true, true, true, true, true)) // y y y y y y y
                    // Add more workouts as needed

                    val workoutDao = db.workoutDao()
                    workoutDao.deleteAll()
                    workoutDao.insertAll(w, ww, www)
                    onResult(true)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }

        // Function to handle database operations in the background
        fun getWorkoutsFromDb(db: AppDatabase, onResult: (Flow<List<Workout>>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val workoutDao = db.workoutDao()
                val workouts = workoutDao.getAll()
                onResult(workouts)
            }
        }

//        // Function to update the UI
//        @OptIn(UnstableApi::class)
//        fun updateUIWithWorkouts(workouts: List<Workout>) {
//            // Your UI update logic here
//            Log.d("Workouts from DB: ", workouts.toString())
//        }


//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                // Perform necessary migration SQL operations here
//                // Example: Add new columns, rename tables, etc.
//                db.execSQL("ALTER TABLE Workout ADD COLUMN days TEXT")
//            }
//        }
    }
}