package com.arctouch.codechallenge.util

class MovieImageUrlBuilder {

    fun buildPosterUrl(posterPath: String): String {
        return "$POSTER_URL$posterPath?api_key=$API_KEY"
    }

    fun buildBackdropUrl(backdropPath: String): String {
        return "$BACKDROP_URL$backdropPath?api_key=$API_KEY"
    }
}
