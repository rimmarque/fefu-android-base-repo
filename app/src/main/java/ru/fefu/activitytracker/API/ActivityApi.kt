package ru.fefu.activitytracker.API

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ActivityApi {

    @POST("/api/auth/register")
    suspend fun register(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("gender") gender: Int,
    ): Token

    @POST("/api/auth/login")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String,
    ): Token

    @POST("/api/auth/logout")
    suspend fun logout(): Unit

    @GET("/api/user/profile")
    suspend fun getProfile(): User
}