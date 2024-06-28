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

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        @OptIn(UnstableApi::class)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateWithDummy(database)
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

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
//                        .addMigrations(MIGRATION_1_2)
                        .addCallback(AppDatabaseCallback(scope))
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

        @OptIn(UnstableApi::class)
        suspend fun populateWithDummy(db: AppDatabase) {
            val workoutDao = db.workoutDao()
            workoutDao.deleteAll()

            val workouts = listOf(
                Workout(name = "Workout A", days = arrayListOf(true, false, true, false, true, false, true)),
                Workout(name = "Workout B", days = arrayListOf(false, true, false, true, false, true, false)),
                Workout(name = "Workout C", days = arrayListOf(true, true, true, true, true, true, true))
            )

            workoutDao.insertAll(*workouts.toTypedArray())
            Log.d("AppDatabase", "Populated database with dummy data.")
        }
    }
}