package ru.fefu.activitytracker

import android.app.Application
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.fefu.activitytracker.API.ActivityApi
import ru.fefu.activitytracker.API.TokenInterceptor
import ru.fefu.activitytracker.room.ActivityDataBase
import java.util.concurrent.TimeUnit

class App: Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    val db by lazy {
        Room.databaseBuilder(
            this,
            ActivityDataBase::class.java,
            "my_database"
        ).allowMainThreadQueries().build()
    }

    val sharedPrefs by lazy {
        getSharedPreferences("shared_prefs", MODE_PRIVATE)
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .callTimeout(10L, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(sharedPrefs))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fefu.t.feip.co/")
            .client(okHttpClient)
            .build()
    }

    val activityApi: ActivityApi by lazy {
        retrofit.create(ActivityApi::class.java)
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }
}