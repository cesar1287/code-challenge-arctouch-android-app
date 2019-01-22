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

    fun loadUpcomingMovies(): LiveData<Resource<UpcomingMoviesResponse>> {
        val mUpcomingMovies: MutableLiveData<Resource<UpcomingMoviesResponse>> = MutableLiveData()
        mUpcomingMovies.value = Resource.loading(null)

        ApiService.getTmdbApiClient().create(TmdbApi::class.java).upcomingMovies(
                API_KEY,
                DEFAULT_LANGUAGE,
                1,
                DEFAULT_REGION
        ).enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                mUpcomingMovies.value = Resource.error(t.localizedMessage, null)
            }

            override fun onResponse(call: Call<UpcomingMoviesResponse>, response: Response<UpcomingMoviesResponse>) {
                if (response.isSuccessful) {
                    val upcomingMoviesList = response.body()
                    mUpcomingMovies.value = Resource.success(upcomingMoviesList)
                } else {
                    val error = ErrorUtils.parseError(response)

                    error?.message?.let {  message ->
                        mUpcomingMovies.value = Resource.error(message, null)
                    } ?: run {
                        mUpcomingMovies.value = Resource.error(ERROR_DEFAULT, null)
                    }
                }
            }
        })

        return mUpcomingMovies
    }
}