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

class UpcomingMoviesViewModel(application: Application): AndroidViewModel(application) {

    //creating livedata for PagedList  and PagedKeyedDataSource
    var itemPagedList: LiveData<PagedList<Movie>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Long, Movie>>? = null

    init {
        //getting our data source factory
        val itemDataSourceFactory = UpcomingMoviesDataSourceFactory()

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource()

        //Getting PagedList config
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE).build()

        //Building the paged list
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)
                .build()
    }
}