package ru.fefu.activitytracker.API

import android.security.NetworkSecurityPolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fefu.activitytracker.App
import java.util.concurrent.AbstractExecutorService
import kotlinx.coroutines.flow.Flow


class LoginRepository {

    private val activityApi = App.INSTANCE.retrofit.create(ActivityApi::class.java)

    suspend fun register(
        login: String,
        password: String,
        name: String,
        gender: Int
    ): Flow<Result<Token>> =
        flow<Result<Token>> {
            emit(
                Result.Success(
                    activityApi.register(
                        login,
                        password,
                        name,
                        gender
                    )
                )
            )
        }
            .catch { emit(Result.Errors(it)) }
            .flowOn(Dispatchers.IO)

    suspend fun login(
        login:String,
        password:String
    ): Flow<Result<Token>> =
        flow<Result<Token>> {
            emit(
                Result.Success(
                    activityApi.login(login, password)
                )
            )
        }
        .catch { emit(Result.Errors(it)) }
        .flowOn(Dispatchers.IO)

    suspend fun getProfile(): Flow<Result<User>> =
        flow<Result<User>> {
            emit(
                Result.Success(
                    activityApi.getProfile()
                )
            )
        }
            .catch { emit(Result.Errors(it)) }
            .flowOn(Dispatchers.IO)

    suspend fun logout(): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            emit(
                Result.Success(
                    activityApi.logout()
                )
            )
        }
            .catch { emit(Result.Errors(it)) }
            .flowOn(Dispatchers.IO)
}
