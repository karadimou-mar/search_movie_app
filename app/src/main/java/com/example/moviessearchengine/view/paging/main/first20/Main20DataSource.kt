package com.example.moviessearchengine.view.paging.main.first20

import androidx.paging.PageKeyedDataSource
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.model.SearchResponse
import com.example.moviessearchengine.network.api.MovieAPIClient
import com.example.moviessearchengine.utils.logD
import com.example.moviessearchengine.utils.logE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO: check loadBefore()

private var count = 0

class Main20DataSource(private val movie: String) : PageKeyedDataSource<Int, Movie>() {

    companion object {
        const val PAGE = 1
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val call: Call<SearchResponse> = MovieAPIClient.getAllResults(movie, PAGE)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                logE("getAll: FAILED", t)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                val resp: SearchResponse? = response.body()
                if (resp?.search != null) {
                    if (resp.totalResults!!.toInt() >= 20){
                        callback.onResult(resp.search as MutableList<Movie>, null, PAGE + 1)
                        count = 1
                    }else {
                        callback.onResult(resp.search as MutableList<Movie>, null, PAGE + 1)
                    }
                } else {
                    logD("No result found!")
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val call: Call<SearchResponse> = MovieAPIClient.getAllResults(movie, params.key)
        call.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                logE("getAll: FAILED", t)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val resp: SearchResponse? = response.body()
                val key = if (response.body() != null) params.key + 1 else null
                if (resp?.search != null) {
                    if (resp.totalResults!!.toInt() >= 20 && count == 1) {
                        callback.onResult(resp.search as MutableList<Movie>, key)
                        count = 0
                    }
                } else {
                    logD("getAll: loadAfter: No result found!")
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val call: Call<SearchResponse> = MovieAPIClient.getAllResults(movie, params.key)
        call.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                logE("getAll: loadBefore: FAILED api connection", t)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val resp: SearchResponse? = response.body()
                val key = if (response.body() != null) params.key - 1 else null
                callback.onResult(resp?.search as MutableList<Movie>, key)
            }
        })
    }
}