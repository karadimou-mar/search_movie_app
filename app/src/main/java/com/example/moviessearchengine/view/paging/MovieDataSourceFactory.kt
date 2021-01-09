package com.example.moviessearchengine.view.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.moviessearchengine.model.Movie

class MovieDataSourceFactory(movie: String) : DataSource.Factory<Int, Movie>() {

    private val m = movie
    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()

    override fun create(): DataSource<Int, Movie> {
        val itemDataSource =
            MovieDataSource(m)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}