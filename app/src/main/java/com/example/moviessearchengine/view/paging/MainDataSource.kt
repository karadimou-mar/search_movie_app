package com.example.moviessearchengine.view.paging

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

class MainDataSource(movie: String) : PageKeyedDataSource<Int, Movie>() {

    private val m = movie

    companion object {
        const val PAGE = 1
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val call: Call<SearchResponse> = MovieAPIClient.getAllResults(m, PAGE)

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
//                val search: List<Movie>? = resp?.search
                if (resp?.search != null) {
                    callback.onResult(resp.search as MutableList<Movie>, null, PAGE + 1)
                } else {
                    logD("No result found!")
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val call: Call<SearchResponse> = MovieAPIClient.getAllResults(m, params.key)
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
//                val search: List<Movie>? = resp?.search
                val key = if (response.body() != null) params.key + 1 else null
                if (resp?.search != null) {
                    callback.onResult(resp.search as MutableList<Movie>, key)
                } else {
                    logD("getAll: loadAfter: No result found!")
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val call: Call<SearchResponse> = MovieAPIClient.getAllResults(m, params.key)
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