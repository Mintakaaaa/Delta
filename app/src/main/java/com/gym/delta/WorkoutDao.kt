package com.gym.delta

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.gym.delta.model.Workout
import com.gym.delta.util.Converters
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(Converters::class)
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout : Workout)

    @Query("SELECT * FROM Workouts")
    fun getAll(): Flow<List<Workout>>

    @Query("UPDATE Workouts SET name = :newName WHERE name = :name")
    suspend fun updateName(name: String, newName: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg workouts: Workout)

    @Query("DELETE FROM Workouts")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(workout: Workout)
}