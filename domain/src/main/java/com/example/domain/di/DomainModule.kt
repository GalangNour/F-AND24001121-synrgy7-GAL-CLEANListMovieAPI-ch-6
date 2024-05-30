package com.example.domain.di

import com.example.domain.repository.MovieRepository
import com.example.domain.usecase.GetMovieDetailUseCase
import com.example.domain.usecase.GetMovieListUseCase
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
}