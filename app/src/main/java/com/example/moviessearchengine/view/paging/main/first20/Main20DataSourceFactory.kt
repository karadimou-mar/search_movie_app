package com.example.moviessearchengine.view.paging.main.first20

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.view.paging.main.MainDataSource

class Main20DataSourceFactory(private val movie: String) : DataSource.Factory<Int, Movie>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()

    override fun create(): DataSource<Int, Movie> {
        val itemDataSource =
            Main20DataSource(movie)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}