package com.example.moviessearchengine.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("totalResults")
    val totalResults: String? = null,
    @SerializedName("Response")
    val response: Boolean? = null,
    @SerializedName("Search")
    val search: List<Movie>? = null)