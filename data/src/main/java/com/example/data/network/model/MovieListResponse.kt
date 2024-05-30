package com.example.data.network.model

import com.example.domain.model.MovieList
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    var results: List<MovieDetailResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
){
    fun toDomain(): MovieList {
        return MovieList(
                results = results.map { it.toDomain() },
                totalResults = totalResults
            )
    }
}

