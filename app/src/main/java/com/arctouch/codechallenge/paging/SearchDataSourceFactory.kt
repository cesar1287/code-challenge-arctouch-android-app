package com.arctouch.codechallenge.paging

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.Resource

class SearchDataSourceFactory(private val query: String) : DataSource.Factory<Long, Movie>() {

    //creating the mutable live data
    private val searchLiveDataSource = MutableLiveData<PageKeyedDataSource<Long, Movie>>()
    private val resourceLiveDataSource = MutableLiveData<Resource>()

    override fun create(): DataSource<Long, Movie> {
        //getting our data source object
        val searchDataSource = SearchPageKeyedDataSource(resourceLiveDataSource, query)

        //posting the datasource to get the values
        searchLiveDataSource.postValue(searchDataSource)

        //returning the datasource
        return searchDataSource
    }

    //getter for itemlivedatasource
    fun getSearchLiveDataSource(): MutableLiveData<PageKeyedDataSource<Long, Movie>> {
        return searchLiveDataSource
    }

    fun getResourceLiveDataSource(): MutableLiveData<Resource> {
        return resourceLiveDataSource
    }
}