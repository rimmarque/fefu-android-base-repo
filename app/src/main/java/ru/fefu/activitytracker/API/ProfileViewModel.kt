package ru.fefu.activitytracker.API

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val loginRepository = LoginRepository()

    private val _profile = MutableSharedFlow<Result<User>>(replay = 0)
    private val _logoutUser = MutableSharedFlow<Result<Unit>>(replay = 0)

    val profile get() = _profile
    val logoutUser get() = _logoutUser

    fun getProfile() {
        viewModelScope.launch {
            loginRepository.getProfile()
                .collect {
                    when(it) {
                        is Result.Success<*> -> _profile.emit(it)
                        is Result.Errors<*> -> _profile.emit(it)
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginRepository.logout()
                .collect {
                    when(it) {
                        is Result.Success<*> -> _logoutUser.emit(it)
                        is Result.Errors<*> -> _logoutUser.emit(it)
                    }
                }
        }
    }
}