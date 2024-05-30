package com.example.challenge_ch6.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.usecase.GetMovieDetailUseCase
import com.example.domain.usecase.GetMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMovieListUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _MovieListState = MutableLiveData<MovieListState>()
    val movieListState = _MovieListState

    private val _MovieDetailState = MutableLiveData<MovieDetailState>()
    val movieDetailState = _MovieDetailState


    fun fetchMoviePlayingNow(){
        getMoviesUseCase().onEach { result ->
            when (result){
                is Resource.Success -> {
                    _MovieListState.value = result.data?.let { MovieListState.Success(it) }
                }
                is Resource.Error -> {
                    _MovieListState.value =
                        MovieListState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _MovieListState.value = MovieListState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDetailMovie(movieId: Int) {
        getMovieDetailUseCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _MovieDetailState.value = result.data?.let { MovieDetailState.Success(it) }
                }
                is Resource.Error -> {
                    _MovieDetailState.value =
                        MovieDetailState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _MovieDetailState.value = MovieDetailState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }
}