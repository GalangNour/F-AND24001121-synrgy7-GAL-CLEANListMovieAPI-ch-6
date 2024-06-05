package com.example.domain.usecase
import com.example.common.Resource
import com.example.domain.model.MovieDetail
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavouriteMovieUseCase @Inject constructor(
   private val movieRepository: MovieRepository
){
    operator fun invoke(movie : MovieDetail) : Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())
        try {
            movieRepository.addFavoriteMovie(movie)
            emit(Resource.Success(movie))
    }catch (e: Exception){
        emit(Resource.Error("Error Occurred"))
        }
    }

}