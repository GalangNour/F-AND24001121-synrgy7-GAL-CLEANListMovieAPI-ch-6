package com.example.challenge_ch6.ui.main

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge_ch6.SharedPreferences
import com.example.challenge_ch6.ui.state.UserState
import com.example.common.Resource
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegisterUserUseCase
import com.example.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserDetailUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _userState = MutableLiveData<UserState>()
    val userState : MutableLiveData<UserState> = _userState

    val userImage: MutableLiveData<Bitmap> = MutableLiveData()


    fun updateUserImage(imageUri: Uri, contentResolver: ContentResolver) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            userImage.value = bitmap
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun registerUser(username: String, password: String, email: String ) {
        registerUserUseCase(username, password, email ).launchIn(viewModelScope)
    }

    fun loginUser(username: String, password: String) {
        loginUserUseCase(username, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userState.value = result.data?.let { UserState.Success(it) }
                    SharedPreferences.isLogin = true
                    SharedPreferences.username = username

                }
                is Resource.Error -> {
                    Log.e("LoginUser", "Error: ${result.message}")
                    _userState.value = UserState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    Log.d("LoginUser", "Loading")
                    _userState.value = UserState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getUserDetail(username: String){
        getUserDetailUseCase.invoke(username).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userState.value = result.data?.let { UserState.Success(it) }
                }
                is Resource.Error -> {
                    Log.e("GetUserDetail", "Error: ${result.message}")
                    _userState.value = UserState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    Log.d("GetUserDetail", "Loading")
                    _userState.value = UserState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUser(username: String, password: String, email: String) {
        updateUserUseCase(username, password, email, SharedPreferences.username).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userState.value = result.data?.let { UserState.Success(it) }
                    SharedPreferences.username = username
                }
                is Resource.Error -> {
                    Log.e("UpdateUser", "Error: ${result.message}")
                    _userState.value = UserState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    Log.d("UpdateUser", "Loading")
                    _userState.value = UserState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }

}