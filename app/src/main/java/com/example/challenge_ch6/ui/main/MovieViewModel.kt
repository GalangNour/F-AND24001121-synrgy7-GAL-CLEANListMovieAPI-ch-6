package com.example.challenge_ch6.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge_ch6.ui.state.FavouriteMovieListState
import com.example.challenge_ch6.ui.state.MovieDetailState
import com.example.challenge_ch6.ui.state.MovieListState
import com.example.common.Resource
import com.example.domain.model.MovieDetail
import com.example.domain.usecase.AddFavouriteMovieUseCase
import com.example.domain.usecase.GetMovieDetailUseCase
import com.example.domain.usecase.GetMovieFavouriteUseCase
import com.example.domain.usecase.GetMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMovieListUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val addFavouriteMovieUseCase: AddFavouriteMovieUseCase,
    private val getFavouriteMoviesUseCase: GetMovieFavouriteUseCase
) : ViewModel() {

    private val _MovieListState = MutableLiveData<MovieListState>()
    val movieListState = _MovieListState

    private val _MovieDetailState = MutableLiveData<MovieDetailState>()
    val movieDetailState = _MovieDetailState

    private val _MovieListFavouriteState = MutableLiveData<FavouriteMovieListState>()
    val movieListFavouriteState = _MovieListFavouriteState


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

    fun addFavouriteMovie(movieDetail: MovieDetail) {
        addFavouriteMovieUseCase(movieDetail).launchIn(viewModelScope)
    }

    fun getFavouriteMovies() {
        getFavouriteMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _MovieListFavouriteState.value =
                        result.data?.let { FavouriteMovieListState.Success(it) }
                }

                is Resource.Error -> {
                    _MovieListFavouriteState.value =
                        FavouriteMovieListState.Error(
                            result.message ?: "An unexpected error occurred"
                        )
                }

                is Resource.Loading -> {
                    _MovieListFavouriteState.value = FavouriteMovieListState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }
}