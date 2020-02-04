package com.example.moviessearchengine.network

import com.example.moviessearchengine.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("s={title}")
    fun getMovies(
        @Path("title") title: String,
        @Query("apiKey") apiKey: String): Call<Movie>

}