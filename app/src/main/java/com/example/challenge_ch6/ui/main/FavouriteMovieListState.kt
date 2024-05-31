package com.example.challenge_ch6.ui.main

import com.example.domain.model.MovieDetail

sealed interface FavouriteMovieListState {
    class Loading : FavouriteMovieListState
    class Success(val movies: List<MovieDetail>) : FavouriteMovieListState
    class Error(val error : String) : FavouriteMovieListState

}