package com.arctouch.codechallenge.model

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>
)