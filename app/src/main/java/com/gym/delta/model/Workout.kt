package com.gym.delta.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gym.delta.util.Converters

@TypeConverters(Converters::class)
@Entity(tableName = "Workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "days") var days: ArrayList<Boolean>?
)