package com.arctouch.codechallenge.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.arctouch.codechallenge.model.Movie
import android.arch.paging.LivePagedListBuilder
import com.arctouch.codechallenge.paging.UpcomingMoviesDataSourceFactory
import com.arctouch.codechallenge.util.PAGE_SIZE
import com.arctouch.codechallenge.util.Resource

class UpcomingMoviesViewModel(application: Application): AndroidViewModel(application) {

    //creating livedata for PagedList  and PagedKeyedDataSource
    var moviePagedList: LiveData<PagedList<Movie>>? = null
    var upcomingMoviesliveDataSource: LiveData<PageKeyedDataSource<Long, Movie>>? = null
    var resourceLiveDataSource: LiveData<Resource>? = null

    init {
        //getting our data source factory
        val upcomingMoviesDataSourceFactory = UpcomingMoviesDataSourceFactory()

        //getting the live data source from data source factory
        upcomingMoviesliveDataSource = upcomingMoviesDataSourceFactory.getUpcomingMoviesLiveDataSource()

        resourceLiveDataSource = upcomingMoviesDataSourceFactory.getResourceLiveDataSource()

        //Getting PagedList config
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE).build()

        //Building the paged list
        moviePagedList = LivePagedListBuilder(upcomingMoviesDataSourceFactory, pagedListConfig)
                .build()
    }
}