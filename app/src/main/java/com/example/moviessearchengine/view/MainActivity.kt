package com.example.moviessearchengine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.MovieAdapter
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.model.SearchResponse
import com.example.moviessearchengine.network.MovieAPIClient
import com.example.moviessearchengine.utils.ListDecorationPadding
import com.example.moviessearchengine.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val movies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideKeyboard()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_movies)

        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = MovieAdapter(movies)

        recyclerView_movies.adapter = adapter

        recyclerView.addItemDecoration(
            ListDecorationPadding(this, 0, 0)

        )

        button_search.setOnClickListener {
            movies.clear()
            hideKeyboard()
            getMovies(editText_search.text.toString())
        }

    }

    private fun getMovies(title: String) {
        val call: Call<SearchResponse> = MovieAPIClient.getMovies(title)
        call.enqueue(object: Callback<SearchResponse>{
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED MOVIE API CONN", t.message)
            }

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val resp: SearchResponse? = response.body()
                Log.d("psofos", "" + response.message())
                Log.d("fuckoff", "" + resp)
                val search: List<Movie>? = resp?.search
                Log.d("maou", ""+search)
                resp?.search?.let { movies.addAll(it) }

                recyclerView_movies.adapter?.notifyDataSetChanged()

            }

        })
    }

}

