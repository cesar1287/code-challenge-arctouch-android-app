package com.arctouch.codechallenge.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository
import com.arctouch.codechallenge.util.Resource

class UpcomingMoviesViewModel(application: Application): AndroidViewModel(application) {

    private var upcomingMoviesRepository: UpcomingMoviesRepository? = null

    init {
        upcomingMoviesRepository = UpcomingMoviesRepository()
    }

    fun getUpcomingMovies(): LiveData<Resource<UpcomingMoviesResponse>>? {
        return upcomingMoviesRepository?.loadUpcomingMovies()
    }
}