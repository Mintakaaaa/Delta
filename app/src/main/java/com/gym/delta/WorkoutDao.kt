package com.gym.delta

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gym.delta.model.Workout

@Dao
interface WorkoutDao {
    @Insert
    fun Insert(workout : Workout)

    @Query("SELECT * FROM workout")
    fun getAll(): List<Workout>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
//    @Insert
//    fun insertAll(vararg users: User)
//
//    @Delete
//    fun delete(user: User)

}