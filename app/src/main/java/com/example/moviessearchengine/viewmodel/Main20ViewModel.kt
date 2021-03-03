package com.example.moviessearchengine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.view.paging.main.first20.Main20DataSourceFactory

class Main20ViewModel(movie: String) : ViewModel() {

    var moviePagedList: LiveData<PagedList<Movie>>

    private var liveDataSource: LiveData<PageKeyedDataSource<Int, Movie>>


    init {
        val dataSourceFactory = Main20DataSourceFactory(movie)
        liveDataSource = dataSourceFactory.itemLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        moviePagedList = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}