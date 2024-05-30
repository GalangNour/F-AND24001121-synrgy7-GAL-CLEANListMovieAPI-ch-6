package com.example.challenge_ch6.ui.main

import com.example.domain.model.MovieList

sealed interface MovieListState {
    class Loading : MovieListState
    class Success(val movies: MovieList) : MovieListState
    class Error(val error : String) : MovieListState

}