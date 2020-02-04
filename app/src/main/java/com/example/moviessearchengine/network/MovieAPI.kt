package com.example.moviessearchengine.network

import com.example.moviessearchengine.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("/")
    fun getMovies(
        @Query("s") title: String,
        @Query("apikey") apikey: String): Call<Movie>

}
