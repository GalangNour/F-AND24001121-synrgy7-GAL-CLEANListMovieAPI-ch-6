package com.example.challenge_ch6.ui.state

import com.example.domain.model.User

sealed interface UserState {
    data object Loading : UserState
    class Success(val user: User) : UserState
    class Error(val error : String) : UserState
}