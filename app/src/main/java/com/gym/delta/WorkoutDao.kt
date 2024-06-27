package com.gym.delta

import androidx.room.Dao
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

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg workouts: Workout)

//    @Delete
//    suspend fun delete(workout: Workout)

    @Query("DELETE FROM Workouts")
    suspend fun deleteAll()

}