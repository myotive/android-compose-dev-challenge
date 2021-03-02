package com.example.androiddevchallenge.database.converter

import androidx.room.TypeConverter
import com.example.androiddevchallenge.database.HairType
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun toHairType(value: Int): HairType =  enumValues<HairType>()[value]

    @TypeConverter
    fun fromHairType(hairType: HairType) = hairType.ordinal
}