package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.model.MovieDetail
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    operator fun invoke(movieId: Int) : Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())
        try {
            val response = movieRepository.getMovieDetail(movieId)
            emit(Resource.Success(response))
        }catch (e: Exception){
            emit(Resource.Error("Error Occurred"))
        }
    }
}