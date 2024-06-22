package com.gym.delta.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?
//    @ColumnInfo(days = Array<Boolean>(7)) val lastName: Boolean?
)