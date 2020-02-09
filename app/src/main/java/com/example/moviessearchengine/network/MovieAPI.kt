package com.example.moviessearchengine.network

import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.model.MovieDetail
import com.example.moviessearchengine.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("/?type=movie")
    fun getMovies(
        @Query("s") title: String,
        @Query("apikey") apikey: String,
        @Query("page") page: Int
    ): Call<SearchResponse>

    @GET("/?plot=full")
    fun getMovieDetails(
        @Query("t") title: String,
        @Query("apikey") apikey: String
    ): Call<MovieDetail>

}
