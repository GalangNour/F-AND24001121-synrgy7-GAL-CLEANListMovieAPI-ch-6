package com.example.challenge_ch6.ui.state

import com.example.domain.model.MovieList

sealed interface MovieListState {
    data object Loading : MovieListState
    class Success(val movies: MovieList) : MovieListState
    class Error(val error : String) : MovieListState

}