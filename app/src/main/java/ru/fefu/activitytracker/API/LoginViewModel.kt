package ru.fefu.activitytracker.API

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val loginRepository = LoginRepository()

    private val _dataFlow = MutableSharedFlow<Result<Token>>(replay = 0)

    val dataFlow get() = _dataFlow

    fun login(login: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(login, password)
                .collect {
                    when(it) {
                        is Result.Success<*> -> _dataFlow.emit(it)
                        is Result.Errors<*> -> _dataFlow.emit(it)
                    }
                }
        }
    }
}