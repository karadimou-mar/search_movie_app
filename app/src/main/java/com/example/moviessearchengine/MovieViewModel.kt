package com.example.moviessearchengine

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.moviessearchengine.model.Movie

class MovieViewModel(movie : String) : ViewModel() {

    var moviePagedList : LiveData<PagedList<Movie>>
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Movie>>


    init {
        val dataSourceFactory = MovieDataSourceFactory(movie)
        liveDataSource = dataSourceFactory.itemLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setMaxSize(35)
            .build()

        moviePagedList = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}