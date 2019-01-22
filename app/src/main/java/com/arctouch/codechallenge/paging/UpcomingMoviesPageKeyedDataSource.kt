package com.arctouch.codechallenge.paging

import android.arch.paging.PageKeyedDataSource
import com.arctouch.codechallenge.api.ApiService
import com.arctouch.codechallenge.api.callbacks.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository
import com.arctouch.codechallenge.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingMoviesPageKeyedDataSource : PageKeyedDataSource<Long, Movie>() {

    private var upcomingMoviesRepository: UpcomingMoviesRepository? = null

    init {
        upcomingMoviesRepository?.let {
            return@let
        } ?: run {
            upcomingMoviesRepository = UpcomingMoviesRepository()
        }
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Movie>) {
        ApiService.getTmdbApiClient().create(TmdbApi::class.java).upcomingMovies(
                API_KEY,
                DEFAULT_LANGUAGE,
                FIRST_PAGE,
                DEFAULT_REGION
        ).enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                //mUpcomingMovies.postValue(Resource.error(t.localizedMessage, null))
            }

            override fun onResponse(call: Call<UpcomingMoviesResponse>, response: Response<UpcomingMoviesResponse>) {
                if (response.isSuccessful) {
                    val moviesList = response.body()?.results
                    moviesList?.let {
                        callback.onResult(moviesList, null, FIRST_PAGE + 1)
                    } ?: run {
                        //TODO LISTA NULL
                    }
                } else {
//                    val error = ErrorUtils.parseError(response)
//
//                    error?.message?.let {  message ->
//                        mUpcomingMovies.value = Resource.error(message, null)
//                    } ?: run {
//                        mUpcomingMovies.value = Resource.error(ERROR_DEFAULT, null)
//                    }
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        val page = params.key

        ApiService.getTmdbApiClient().create(TmdbApi::class.java).upcomingMovies(
                API_KEY,
                DEFAULT_LANGUAGE,
                page,
                DEFAULT_REGION
        ).enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                //mUpcomingMovies.postValue(Resource.error(t.localizedMessage, null))
            }

            override fun onResponse(call: Call<UpcomingMoviesResponse>, response: Response<UpcomingMoviesResponse>) {
                if (response.isSuccessful) {
                    val moviesList = response.body()?.results
                    moviesList?.let {
                        callback.onResult(moviesList, page + 1)
                    } ?: run {
                        //TODO LISTA NULL
                    }
                } else {
//                    val error = ErrorUtils.parseError(response)
//
//                    error?.message?.let {  message ->
//                        mUpcomingMovies.value = Resource.error(message, null)
//                    } ?: run {
//                        mUpcomingMovies.value = Resource.error(ERROR_DEFAULT, null)
//                    }
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        val page = params.key

        ApiService.getTmdbApiClient().create(TmdbApi::class.java).upcomingMovies(
                API_KEY,
                DEFAULT_LANGUAGE,
                page,
                DEFAULT_REGION
        ).enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                //mUpcomingMovies.postValue(Resource.error(t.localizedMessage, null))
            }

            override fun onResponse(call: Call<UpcomingMoviesResponse>, response: Response<UpcomingMoviesResponse>) {
                if (response.isSuccessful) {
                    val moviesList = response.body()?.results
                    moviesList?.let {
                        callback.onResult(moviesList, page - 1)
                    } ?: run {
                        //TODO LISTA NULL
                    }
                } else {
//                    val error = ErrorUtils.parseError(response)
//
//                    error?.message?.let {  message ->
//                        mUpcomingMovies.value = Resource.error(message, null)
//                    } ?: run {
//                        mUpcomingMovies.value = Resource.error(ERROR_DEFAULT, null)
//                    }
                }
            }
        })
    }
}