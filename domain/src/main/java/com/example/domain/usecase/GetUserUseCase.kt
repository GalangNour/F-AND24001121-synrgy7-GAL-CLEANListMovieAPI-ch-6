package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(username: String) : Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = userRepository.getUser(username)
            emit(Resource.Success(response))
        }catch (e: Exception){
            emit(Resource.Error("Error Occured"))
        }
    }
}