package com.example.domain.model

data class MovieList(
    var results: List<MovieDetail>,
    val totalResults: Int
)

