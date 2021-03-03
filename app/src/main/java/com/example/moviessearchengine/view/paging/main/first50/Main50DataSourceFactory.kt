package com.example.moviessearchengine.view.paging.main.first50

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.view.paging.main.MainDataSource

class Main50DataSourceFactory(private val movie: String) : DataSource.Factory<Int, Movie>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()

    override fun create(): DataSource<Int, Movie> {
        val itemDataSource =
            Main50DataSource(movie)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}