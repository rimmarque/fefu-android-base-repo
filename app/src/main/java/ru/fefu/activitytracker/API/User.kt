package ru.fefu.activitytracker.API

data class User(
    val id: Int,
    val name: String,
    val login: String,
    val gender: Gender,
)
