package com.example.moviessearchengine.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Rated")
    val rated: String? = null,
    @SerializedName("Runtime")
    val runtime: String? = null,
    @SerializedName("Genre")
    val genre: String? = null,
    @SerializedName("Released")
    val released: String? = null,
    @SerializedName("Plot")
    val plot: String? = null,
    @SerializedName("Director")
    val director: String? = null,
    @SerializedName("Writer")
    val writer: String? = null,
    @SerializedName("Actors")
    val actor: String? = null,
    @SerializedName("Metascore")
    val metascore: String? = null,
    @SerializedName("imdbRating")
    val imdbRating: String? = null,
    @SerializedName("Ratings")
    val rating: ArrayList<Rating>? = null
)