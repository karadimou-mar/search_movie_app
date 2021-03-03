package com.example.moviessearchengine.view.ui

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.network.api.MovieAPIClient.getMovieDetails
import com.example.moviessearchengine.network.connectivity.ConnectivityReceiver
import com.example.moviessearchengine.utils.ListDecorationPadding
import com.example.moviessearchengine.utils.hideKeyboard
import com.example.moviessearchengine.utils.visibleElseGone
import com.example.moviessearchengine.view.adapter.MovieAdapter
import com.example.moviessearchengine.viewmodel.MainViewModel
import com.example.moviessearchengine.viewmodel.MovieViewModel
import com.example.moviessearchengine.viewmodel.SeriesViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.searchImage
import kotlinx.android.synthetic.main.activity_main.view.*

//TODO: synthetic kotlin change
//TODO: onfailure

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener,
    ConnectivityReceiver.ConnectivityReceiverListener {

    private var snackBar: Snackbar? = null
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideKeyboard()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val recyclerView = findViewById<MovieRecyclerView>(R.id.resultsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(this, applicationContext)
        recyclerView.adapter = adapter
        recyclerView.setNoResultImage(imageView_no_result)
        recyclerView.setProgressBar(progressBar)
        recyclerView.addItemDecoration(
            ListDecorationPadding(this, 0, 0)
        )

        search.setOnClickListener {
            searchImage.visibility = View.GONE
            hideKeyboard()
            checkConnection()
            getAllResults(searchEditText.text.toString(), this, adapter)
        }
        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun getAllResults(title: String, activity: AppCompatActivity, adapter: MovieAdapter) {
        activity.viewModelStore.clear()
        val movieViewModel = ViewModelProviders.of(activity, MyMainViewModelFactory(title))
            .get<MainViewModel>(MainViewModel::class.java)

        movieViewModel.moviePagedList.observe(activity, Observer { items ->
            adapter.submitList(items)
        })
    }

    private fun getMovies(title: String, activity: AppCompatActivity, adapter: MovieAdapter) {
        activity.viewModelStore.clear()
        val movieViewModel = ViewModelProviders.of(activity, MyMovieViewModelFactory(title))
            .get<MovieViewModel>(MovieViewModel::class.java)

        movieViewModel.moviePagedList.observe(activity, Observer { items ->
            adapter.submitList(items)
        })
    }

    private fun getSeries(title: String, activity: AppCompatActivity, adapter: MovieAdapter) {
        activity.viewModelStore.clear()
        val movieViewModel = ViewModelProviders.of(activity, MySeriesViewModelFactory(title))
            .get<SeriesViewModel>(SeriesViewModel::class.java)

        movieViewModel.moviePagedList.observe(activity, Observer { items ->
            adapter.submitList(items)
        })
    }

    override fun onItemClicked(movie: Movie?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("title", movie?.title)
        intent.putExtra("poster", movie?.poster)
        this.startActivity(intent)
        //TODO: remove from here??
        getMovieDetails(movie?.title.toString())
    }

    class MyMainViewModelFactory(private val mParam: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(mParam) as T
        }
    }

    class MyMovieViewModelFactory(private val mParam: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieViewModel(mParam) as T
        }
    }

    class MySeriesViewModelFactory(private val mParam: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SeriesViewModel(mParam) as T
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackBar = Snackbar.make(
                findViewById(R.id.constraint_layout),
                getString(R.string.network_error),
                Snackbar.LENGTH_LONG
            )
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }

    private fun checkConnection(): Boolean {
        val isConnected = ConnectivityReceiver.isConnectedOrConnecting(this)
        showNetworkMessage(isConnected)
        return isConnected
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.movie_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.movies -> {
                item.isChecked = !item.isChecked
                searchImage.visibleElseGone { !searchImage.isVisible }
                getMovies(searchEditText.text.toString(), this, adapter)
                true
            }
            R.id.series -> {
                item.isChecked = !item.isChecked
                searchImage.visibleElseGone { !searchImage.isVisible }
                getSeries(searchEditText.text.toString(), this, adapter)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

