package ru.fefu.activitytracker.lists

class UsersListRepository {
    private val usersListItem = listOf(
        ListItem.Date(
            date = "Вчера",
        ),
        ListItem.Item(
            distance = "14.32 км",
            time = "2 часа 46 минут",
            activity = "Серфинг",
            date = "14 часов назад",
            startTime = "13:00",
            endTime = "15:46",
            user = "@van_darkholme",
        ),
        ListItem.Item(
            distance = "228 м",
            time = "14 часов 48 минут",
            activity = "Качели",
            date = "14 часов назад",
            startTime = "00:00",
            endTime = "14:48",
            user = "@techniquepasha",
        ),
        ListItem.Item(
            distance = "10 км",
            time = "1 час 10 минут",
            activity = "Езда на кадилак",
            date = "14 часов назад",
            startTime = "20:35",
            endTime = "21:45",
            user = "@morgen_shtern",
        ),
    )

    fun getItem() : List<ListItem> = usersListItem
}