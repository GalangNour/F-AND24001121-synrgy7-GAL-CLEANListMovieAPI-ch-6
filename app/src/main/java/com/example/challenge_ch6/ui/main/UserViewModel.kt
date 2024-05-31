package com.example.challenge_ch6.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge_ch6.ui.state.UserState
import com.example.common.Resource
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _UserState = MutableLiveData<UserState>()
    val userState : MutableLiveData<UserState> = _UserState

    fun registerUser(username: String, password: String, email: String ) {
        registerUserUseCase(username, password, email ).launchIn(viewModelScope)
    }

    fun loginUser(username: String, password: String) {
        loginUserUseCase(username, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _UserState.value = result.data?.let { UserState.Success(it)}
                }
                is Resource.Error -> {
                    _UserState.value = UserState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _UserState.value = UserState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }

}