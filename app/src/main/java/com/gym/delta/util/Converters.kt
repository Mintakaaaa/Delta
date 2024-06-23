package com.gym.delta.util

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun arrayListToJsonString(value: ArrayList<Boolean>): String = Gson().toJson(value)

//    @TypeConverter
//    fun jsonStringToArrayList(value: String) = Gson().fromJson(value, Array<Boolean>::class.java).toList()
    @TypeConverter
    fun jsonStringToArrayList(value: String): ArrayList<Boolean>? {
        val array = Gson().fromJson(value, Array<Boolean>::class.java)
        return array?.toList()?.toMutableList() as ArrayList<Boolean>?
    }
}