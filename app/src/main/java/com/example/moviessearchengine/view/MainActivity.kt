package com.example.moviessearchengine.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.MovieAdapter
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.model.MovieDetail
import com.example.moviessearchengine.model.SearchResponse
import com.example.moviessearchengine.network.MovieAPIClient
import com.example.moviessearchengine.utils.ListDecorationPadding
import com.example.moviessearchengine.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO: synthetic kotlin change
//TODO: onfailure

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {

    val movies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideKeyboard()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_movies)

        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = MovieAdapter(movies, this)

        recyclerView.adapter = adapter

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
                Log.e("getMovies FAILED", t.message!!)
            }

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val resp: SearchResponse? = response.body()
                val search: List<Movie>? = resp?.search
                Log.d("maou", ""+search)
                resp?.search?.let { movies.addAll(it) }

                recyclerView_movies.adapter?.notifyDataSetChanged()

            }

        })
    }

    private fun getMovieDetails(title: String){
        val call: Call<MovieDetail> = MovieAPIClient.getMovieDetails(title)
        call.enqueue(object: Callback<MovieDetail>{
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                t.printStackTrace()
                Log.e("getMovieDetails FAILED", t.message!!)
            }

            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                val resp: MovieDetail? = response.body()
                Log.d("BOOM",""+resp)
            }

        })
    }

    override fun onItemClicked(movie: Movie?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("title", movie?.title)
        intent.putExtra("poster", movie?.poster)
        this.startActivity(intent)
        getMovieDetails(movie?.title.toString())
    }

}

