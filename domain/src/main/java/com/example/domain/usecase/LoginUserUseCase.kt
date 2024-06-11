package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(username: String, password: String) : Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val user = userRepository.login(username, password)
            if (user != null) {
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("User not found"))
            }
        }catch (e: Exception){
            emit(Resource.Error("Error Occurred"))
        }
    }
}