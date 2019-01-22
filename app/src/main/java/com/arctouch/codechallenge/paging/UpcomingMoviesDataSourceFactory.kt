package com.arctouch.codechallenge.paging

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.model.Movie

class UpcomingMoviesDataSourceFactory : DataSource.Factory<Long, Movie>() {

    //creating the mutable live data
    private val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Long, Movie>>()

    override fun create(): DataSource<Long, Movie> {
        //getting our data source object
        val itemDataSource = UpcomingMoviesPageKeyedDataSource()

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource)

        //returning the datasource
        return itemDataSource
    }

    //getter for itemlivedatasource
    fun getItemLiveDataSource(): MutableLiveData<PageKeyedDataSource<Long, Movie>> {
        return itemLiveDataSource
    }
}