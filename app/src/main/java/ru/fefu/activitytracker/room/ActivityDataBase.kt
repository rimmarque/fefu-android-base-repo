package ru.fefu.activitytracker.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [ActivityRoom::class], version = 1)
@TypeConverters(Converter::class)
abstract class ActivityDataBase: RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}