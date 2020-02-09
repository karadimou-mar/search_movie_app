package com.example.moviessearchengine.view

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.MovieAdapter
import com.example.moviessearchengine.MovieViewModel
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.network.MovieAPIClient.getMovieDetails
import com.example.moviessearchengine.utils.ListDecorationPadding
import com.example.moviessearchengine.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*


//TODO: synthetic kotlin change
//TODO: onfailure
//TODO: show/hide progressbar

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {


    val movies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideKeyboard()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_movies)

        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = MovieAdapter( this)

        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            ListDecorationPadding(this, 0, 0)

        )


        button_search.setOnClickListener {
            imageView_search.visibility = View.GONE
            hideKeyboard()
            getMovies(editText_search.text.toString(),this,adapter)
            recyclerView.visibility = View.VISIBLE

        }



    }

    private fun getMovies(title: String ,activity : AppCompatActivity, adapter: MovieAdapter) {
        activity.viewModelStore.clear()
        val movieViewModel = ViewModelProviders.of(activity, MyViewModelFactory(title)).get<MovieViewModel>(
            MovieViewModel::class.java)

        movieViewModel.moviePagedList.observe(activity, Observer { items ->
            adapter.submitList(items)

        })
//        val call: Call<SearchResponse> = MovieAPIClient.getMovies(title)
//        call.enqueue(object: Callback<SearchResponse>{
//            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
//                t.printStackTrace()
//                Log.e("getMovies FAILED", t.message!!)
//            }
//
//            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
//                val resp: SearchResponse? = response.body()
//                val search: List<Movie>? = resp?.search
//                resp?.search?.let { movies.addAll(it) }
//
//                recyclerView_movies.adapter?.notifyDataSetChanged()
//
//            }
//
//        })
    }


    override fun onItemClicked(movie: Movie?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("title", movie?.title)
        intent.putExtra("poster", movie?.poster)
        this.startActivity(intent)
        //TODO: remove from here
        getMovieDetails(movie?.title.toString())
    }

    class MyViewModelFactory( private val mParam: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieViewModel( mParam) as T
        }

    }

}

