package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(username: String, password: String, email: String) : Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            userRepository.register(username, password, email)
            emit(Resource.Success(User(username, password, email)))
        }catch (e: Exception){
            emit(Resource.Error("Error Occurred"))
        }

    }

}