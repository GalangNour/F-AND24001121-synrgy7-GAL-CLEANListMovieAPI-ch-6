package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(newUsername: String, newPassword: String, newEmail: String, username: String) : Flow<Resource<User>> = flow{
        emit(Resource.Loading())
        try {
            userRepository.updateUser(newUsername, newPassword, newEmail, username)
            emit(Resource.Success(User(newUsername, newPassword, newEmail)))
        }catch (e: Exception){
            emit(Resource.Error("Error Occurred"))
        }
    }
}