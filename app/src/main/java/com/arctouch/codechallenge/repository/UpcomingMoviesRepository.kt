package com.arctouch.codechallenge.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.ApiService
import com.arctouch.codechallenge.api.callbacks.TmdbApi
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingMoviesRepository {

    fun loadUpcomingMovies(page: Long): LiveData<Resource<UpcomingMoviesResponse>> {
        val mUpcomingMovies: MutableLiveData<Resource<UpcomingMoviesResponse>> = MutableLiveData()
        mUpcomingMovies.postValue(Resource.loading(null))

        ApiService.getTmdbApiClient().create(TmdbApi::class.java).upcomingMovies(
                API_KEY,
                DEFAULT_LANGUAGE,
                page,
                DEFAULT_REGION
        ).enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                mUpcomingMovies.postValue(Resource.error(t.localizedMessage, null))
            }

            override fun onResponse(call: Call<UpcomingMoviesResponse>, response: Response<UpcomingMoviesResponse>) {
                if (response.isSuccessful) {
                    val upcomingMoviesList = response.body()
                    mUpcomingMovies.postValue(Resource.success(upcomingMoviesList))
                } else {
                    val error = ErrorUtils.parseError(response)

                    error?.message?.let {  message ->
                        mUpcomingMovies.postValue(Resource.error(message, null))
                    } ?: run {
                        mUpcomingMovies.postValue(Resource.error(ERROR_DEFAULT, null))
                    }
                }
            }
        })

        return mUpcomingMovies
    }
}