package com.example.challenge_ch6.ui.main

import com.example.domain.model.MovieDetail

sealed interface MovieDetailState {
    class Loading : MovieDetailState
    class Success(val moviesDetail: MovieDetail) : MovieDetailState
    class Error(val error : String) : MovieDetailState
}