package com.arctouch.codechallenge.api.callbacks

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("genre/movie/list")
    fun genres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Long,
        @Query("region") region: String
    ): Call<UpcomingMoviesResponse>

    @GET("search/movie")
    fun searchMovies(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Long
    ): Call<UpcomingMoviesResponse>
}
