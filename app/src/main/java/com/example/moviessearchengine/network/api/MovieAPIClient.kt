package com.example.moviessearchengine.network.api

import com.example.moviessearchengine.BuildConfig
import com.example.moviessearchengine.model.MovieDetail
import com.example.moviessearchengine.model.SearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MovieAPIClient {

    private const val BASE_URL = "http://www.omdbapi.com"

    private val api: MovieAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getAllResults(title: String, page: Int): Call<SearchResponse>{
        return api.getAllResults(title, BuildConfig.API_KEY, page)
    }

    fun getMovies(title: String, page: Int): Call<SearchResponse>{
        return api.getMovies(title, BuildConfig.API_KEY, page)
    }

    fun getSeries(title: String, page: Int): Call<SearchResponse>{
        return api.getSeries(title, BuildConfig.API_KEY, page)
    }

    fun getMovieDetails(title: String): Call<MovieDetail>{
        return api.getMovieDetails(title, BuildConfig.API_KEY)
    }
}