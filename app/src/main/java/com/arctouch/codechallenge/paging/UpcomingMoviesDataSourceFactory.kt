package com.arctouch.codechallenge.paging

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.Resource

class UpcomingMoviesDataSourceFactory : DataSource.Factory<Long, Movie>() {

    //creating the mutable live data
    private val upcomingMoviesLiveDataSource = MutableLiveData<PageKeyedDataSource<Long, Movie>>()
    private val resourceLiveDataSource = MutableLiveData<Resource>()

    override fun create(): DataSource<Long, Movie> {
        //getting our data source object
        val upcomingMoviesDataSource = UpcomingMoviesPageKeyedDataSource(resourceLiveDataSource)

        //posting the datasource to get the values
        upcomingMoviesLiveDataSource.postValue(upcomingMoviesDataSource)

        //returning the datasource
        return upcomingMoviesDataSource
    }

    //getter for itemlivedatasource
    fun getUpcomingMoviesLiveDataSource(): MutableLiveData<PageKeyedDataSource<Long, Movie>> {
        return upcomingMoviesLiveDataSource
    }

    fun getResourceLiveDataSource(): MutableLiveData<Resource> {
        return resourceLiveDataSource
    }
}