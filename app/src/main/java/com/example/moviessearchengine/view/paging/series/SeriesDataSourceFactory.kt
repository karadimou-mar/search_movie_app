package com.example.moviessearchengine.view.paging.series

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.view.paging.movies.SeriesDataSource

class SeriesDataSourceFactory(movie: String) : DataSource.Factory<Int, Movie>() {

    private val m = movie
    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()

    override fun create(): DataSource<Int, Movie> {
        val itemDataSource =
            SeriesDataSource(m)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}