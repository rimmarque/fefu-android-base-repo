package ru.fefu.activitytracker.lists

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class UsersListRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    private val usersListItem = listOf(
        ListItem.Date(
            date = "Вчера",
        ),
        ListItem.Item(
            id = 125,
            distance = "14.32 км",
            time = "2 часа 46 минут",
            activity = "Серфинг",
            date = "14 часов назад",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            user = "@van_darkholme",
        ),
        ListItem.Item(
            id = 133,
            distance = "228 м",
            time = "14 часов 48 минут",
            activity = "Качели",
            date = "14 часов назад",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            user = "@techniquepasha",
        ),
        ListItem.Item(
            id = 15,
            distance = "10 км",
            time = "1 час 10 минут",
            activity = "Езда на кадилак",
            date = "14 часов назад",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            user = "@morgen_shtern",
        ),
    )

    fun getItem() : List<ListItem> = usersListItem
}