package com.example.data.mappers

import com.example.data.network.model.MovieListResponse
import com.example.domain.model.MovieList

fun List<MovieListResponse>.toDomain(): List<MovieList> {
    return map {
        MovieList(
            results = it.results.map { it.toDomain() },
            totalResults = it.totalResults
        )
    }
}
