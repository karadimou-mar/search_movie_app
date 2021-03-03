package com.example.moviessearchengine.view.paging.main

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.moviessearchengine.model.Movie

class MainDataSourceFactory(private val movie: String) : DataSource.Factory<Int, Movie>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()

    override fun create(): DataSource<Int, Movie> {
        val itemDataSource =
            MainDataSource(movie)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}