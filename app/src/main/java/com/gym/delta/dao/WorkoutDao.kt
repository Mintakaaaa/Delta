package com.gym.delta.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.gym.delta.model.Workout
import com.gym.delta.util.Converters

@Dao
@TypeConverters(Converters::class)
interface WorkoutDao {
    @Insert
    fun Insert(workout : Workout)

    @Query("SELECT * FROM Workouts")
    fun getAll(): List<Workout>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
    @Insert
    fun insertAll(vararg workouts: Workout)

//    @Delete
//    fun delete(user: User)

    @Query("DELETE FROM Workouts")
    fun deleteAllWorkouts()

}