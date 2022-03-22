package ru.fefu.activitytracker.lists

sealed class ListItem {
    data class Item(
        val distance: String,
        val time: String,
        val date: String,
        val activity: String,
        val startTime: String,
        val endTime: String,
        val user: String = "",
    ) : ListItem()

    data class Date(
        val date: String,
    ) : ListItem()
}