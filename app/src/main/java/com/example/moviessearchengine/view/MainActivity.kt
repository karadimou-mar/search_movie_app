package com.example.moviessearchengine.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.ConnectivityReceiver
import com.example.moviessearchengine.MovieAdapter
import com.example.moviessearchengine.MovieViewModel
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.network.MovieAPIClient.getMovieDetails
import com.example.moviessearchengine.utils.ListDecorationPadding
import com.example.moviessearchengine.utils.hideKeyboard
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


//TODO: synthetic kotlin change
//TODO: onfailure
//TODO: show/hide progressbar

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideKeyboard()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_movies)

        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = MovieAdapter(this)

        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            ListDecorationPadding(this, 0, 0)

        )

        button_search.setOnClickListener {
            imageView_search.visibility = View.GONE
            hideKeyboard()
            checkConnection()
            getMovies(editText_search.text.toString(), this, adapter)
            recyclerView.visibility = View.VISIBLE
            //imageView_no_result.visibility = View.GONE

        }
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun getMovies(title: String, activity: AppCompatActivity, adapter: MovieAdapter) {
        activity.viewModelStore.clear()
        val movieViewModel =
            ViewModelProviders.of(activity, MyViewModelFactory(title)).get<MovieViewModel>(
                MovieViewModel::class.java
            )

        movieViewModel.moviePagedList.observe(activity, Observer { items ->
            adapter.submitList(items)

//            imageView_no_result.visibility = View.VISIBLE
//            recyclerView_movies.setBackgroundColor(Color.WHITE)

        })



    }


    override fun onItemClicked(movie: Movie?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("title", movie?.title)
        intent.putExtra("poster", movie?.poster)
        this.startActivity(intent)
        //TODO: remove from here
        getMovieDetails(movie?.title.toString())
    }

    class MyViewModelFactory(private val mParam: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieViewModel(mParam) as T
        }

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected){
            snackBar = Snackbar.make(findViewById(R.id.constraint_layout), "You are offline. Check your network!", Snackbar.LENGTH_LONG)
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        }else{
            snackBar?.dismiss()
        }
    }

    private fun checkConnection(): Boolean {
        val isConnected = ConnectivityReceiver.isConnectedOrConnecting(this)
        showNetworkMessage(isConnected)
        return isConnected
    }

}

