package com.arctouch.codechallenge.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.arctouch.codechallenge.model.Movie
import android.arch.paging.LivePagedListBuilder
import com.arctouch.codechallenge.paging.SearchDataSourceFactory
import com.arctouch.codechallenge.preference.MainPreference
import com.arctouch.codechallenge.util.PAGE_SIZE
import com.arctouch.codechallenge.util.Resource

class SearchViewModel(application: Application): AndroidViewModel(application) {

    //creating livedata for PagedList  and PagedKeyedDataSource
    var moviePagedList: LiveData<PagedList<Movie>>? = null
    private var searchliveDataSource: LiveData<PageKeyedDataSource<Long, Movie>>? = null
    var resourceLiveDataSource: LiveData<Resource>? = null

    init {
        val query = MainPreference.getUserReference(application)

        query?.let { queryNonNull ->
            //getting our data source factory
            val searchDataSourceFactory = SearchDataSourceFactory(queryNonNull)

            //getting the live data source from data source factory
            searchliveDataSource = searchDataSourceFactory.getSearchLiveDataSource()

            resourceLiveDataSource = searchDataSourceFactory.getResourceLiveDataSource()

            //Getting PagedList config
            val pagedListConfig = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(PAGE_SIZE).build()

            //Building the paged list
            moviePagedList = LivePagedListBuilder(searchDataSourceFactory, pagedListConfig)
                    .build()
        }
    }
}