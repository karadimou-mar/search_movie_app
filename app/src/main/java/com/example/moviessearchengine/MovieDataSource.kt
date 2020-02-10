package com.example.moviessearchengine

import android.util.Log
import android.widget.Toast
import androidx.paging.PageKeyedDataSource
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.model.SearchResponse
import com.example.moviessearchengine.network.MovieAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO: check loadBefore()

class MovieDataSource(movie:String): PageKeyedDataSource<Int, Movie>() {


    private val m = movie
    companion object{
        const val PAGE = 1
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val call: Call<SearchResponse> = MovieAPIClient.getMovies(m,PAGE)
        call.enqueue(object: Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("getMovies FAILED", t.message!!)
            }

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val resp: SearchResponse? = response.body()
//                val search: List<Movie>? = resp?.search
                if (resp?.search != null) {
                    callback.onResult(resp?.search as MutableList<Movie>, null, PAGE + 1)
                }else{
                    Log.d("NO_RESULT", "No result found!")

                }
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val call: Call<SearchResponse> = MovieAPIClient.getMovies(m,params.key)
        call.enqueue(object: Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("getMovies FAILED", t.message!!)
            }

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val resp: SearchResponse? = response.body()
//                val search: List<Movie>? = resp?.search
                val key = if (response.body() != null) params.key + 1 else null
                callback.onResult(resp?.search as MutableList<Movie>, key)

            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val call: Call<SearchResponse> = MovieAPIClient.getMovies(m,params.key)
        call.enqueue(object: Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED API CONNECTION", t.message)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val resp: SearchResponse? = response.body()
                val key = if (response.body()!=null) params.key - 1 else null
                callback.onResult(resp?.search as MutableList<Movie>, key)
            }

        })
    }
}