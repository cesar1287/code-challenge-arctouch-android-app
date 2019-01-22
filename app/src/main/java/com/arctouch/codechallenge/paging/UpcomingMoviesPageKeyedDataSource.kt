package com.arctouch.codechallenge.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.arctouch.codechallenge.api.ApiService
import com.arctouch.codechallenge.api.callbacks.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingMoviesPageKeyedDataSource(var resourceLiveDataSource: MutableLiveData<Resource>) : PageKeyedDataSource<Long, Movie>() {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Movie>) {
        resourceLiveDataSource.postValue(Resource.loading())

        ApiService.getTmdbApiClient().create(TmdbApi::class.java).upcomingMovies(
                API_KEY,
                DEFAULT_LANGUAGE,
                FIRST_PAGE,
                DEFAULT_REGION
        ).enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                resourceLiveDataSource.postValue(Resource.error(t.localizedMessage))
                callback.onResult(mutableListOf(), null, FIRST_PAGE + 1)
            }

            override fun onResponse(call: Call<UpcomingMoviesResponse>, response: Response<UpcomingMoviesResponse>) {
                if (response.isSuccessful) {
                    val moviesList = response.body()?.results
                    moviesList?.let {
                        resourceLiveDataSource.postValue(Resource.success())
                        callback.onResult(moviesList, null, FIRST_PAGE + 1)
                    } ?: run {
                        resourceLiveDataSource.postValue(Resource.error(ERROR_DEFAULT))
                        callback.onResult(mutableListOf(), null, FIRST_PAGE + 1)
                    }
                } else {
                    val error = ErrorUtils.parseError(response)

                    error?.message?.let {  message ->
                        resourceLiveDataSource.postValue(Resource.error(message))
                    } ?: run {
                        resourceLiveDataSource.postValue(Resource.error(ERROR_DEFAULT))
                    }
                    callback.onResult(mutableListOf(), null, FIRST_PAGE + 1)
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