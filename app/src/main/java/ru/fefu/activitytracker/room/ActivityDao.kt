package ru.fefu.activitytracker.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// запросы в таблицу
@Dao
public interface ActivityDao {

    @Query("SELECT * FROM ActivityRoom ORDER BY end_time DESC")
    fun getAll(): LiveData<List<ActivityRoom>>

    @Query("SELECT * FROM ActivityRoom WHERE id = :id")
    fun getById(id: Int): ActivityRoom

    @Insert
    fun insert(activity: ActivityRoom)

    @Delete
    fun delete(activity: ActivityRoom)
}
