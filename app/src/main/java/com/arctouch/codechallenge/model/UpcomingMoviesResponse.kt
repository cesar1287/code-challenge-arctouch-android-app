package com.arctouch.codechallenge.model

import com.google.gson.annotations.SerializedName

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int
)