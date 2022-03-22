package ru.fefu.activitytracker.lists

class MyListRepository {
    private val listItem = listOf(
        ListItem.Date(
            date = "Вчера"
        ),

        ListItem.Item(
            distance = "14.35 км",
            time = "2 часа 46 минут",
            date = "14 часов назад",
            startTime = "14:15",
            endTime = "17:01",
            activity = "Серфинг \uD83C\uDFC4",
        ),

        ListItem.Date(
            date = "Май 2022 года"
        ),

        ListItem.Item(
            distance = "1 000 м",
            time = "60 минут",
            date = "29.05.2022",
            startTime = "12:03",
            endTime = "13:03",
            activity = "Велосипед  \uD83D\uDEB2",
        )
    )

    fun getItem() : List<ListItem> = listItem
}