package com.example.domain.di

import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.AddFavouriteMovieUseCase
import com.example.domain.usecase.GetMovieDetailUseCase
import com.example.domain.usecase.GetMovieFavouriteUseCase
import com.example.domain.usecase.GetMovieListUseCase
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Provides
    fun provideGetMovieUseCase(movieRepository: MovieRepository): GetMovieListUseCase {
        return GetMovieListUseCase(movieRepository = movieRepository)
    }

    @Provides
    fun provideGetDetailUseCase(movieRepository: MovieRepository): GetMovieDetailUseCase{
        return GetMovieDetailUseCase(movieRepository = movieRepository)
    }

    @Provides
    fun provideAddFavouriteMovieUseCase(movieRepository: MovieRepository): AddFavouriteMovieUseCase {
        return AddFavouriteMovieUseCase(movieRepository = movieRepository)
    }

    @Provides
    fun GetMovieFavouriteUseCaset(movieRepository: MovieRepository): GetMovieFavouriteUseCase {
        return GetMovieFavouriteUseCase(movieRepository = movieRepository)
    }

    @Provides
    fun provideLoginUserUseCase(userRepository: UserRepository): LoginUserUseCase {
        return LoginUserUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUserUseCase {
        return RegisterUserUseCase(userRepository = userRepository)
    }

}