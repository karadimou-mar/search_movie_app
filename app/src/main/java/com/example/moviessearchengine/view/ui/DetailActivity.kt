package com.example.moviessearchengine.view.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.MovieDetail
import com.example.moviessearchengine.network.api.MovieAPIClient
import com.example.moviessearchengine.utils.loadPoster
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.textView_title
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO: imageview fit

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        getIntentExtras()

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        getMovieDetails(textView_title.text.toString())
    }

    private fun getIntentExtras() {
        val bundle: Bundle? = intent.extras
        val title = bundle?.getString("title")
        val poster: String? = bundle?.getString("poster")
        setIntentExtras(title, poster)

    }

    private fun setIntentExtras(title: String?, poster: String?) {

        textView_title.text = title
        imageView_poster.loadPoster(poster)


    }

    private fun getMovieDetails(title: String) {
        val call: Call<MovieDetail> = MovieAPIClient.getMovieDetails(title)
        call.enqueue(object : Callback<MovieDetail> {
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                t.printStackTrace()
                Log.e("getMovieDetails FAILED", t.message!!)
            }

            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                val resp: MovieDetail? = response.body()
                Log.d("BOOM", "" + resp)
//                try {
//                    Thread.sleep(2000)
//                    progress_bar_detail.visibility = View.VISIBLE
//                }catch (e: Exception){
//                    e.printStackTrace()
//                }
                textView_plot.text = resp?.plot
                textView_directed.text = resp?.director
                textView_written.text = resp?.writer
                textView_starring.text = resp?.actor
                textView_details.text =
                    resp?.rated + getString(R.string.movie_detail) + resp?.runtime + getString(R.string.movie_detail) + resp?.genre + getString(
                        R.string.movie_detail
                    ) + resp?.released
                textView_imdb.text = resp?.imdbRating
                for (i in 0 until resp?.rating!!.size) {
                    if (resp.rating[i].source.isNullOrEmpty()) {
                        textView_rt.text = "N/A"
                    } else {
                        textView_rt.text = resp.rating[i].value
                    }
                }
                textView_mc.text = resp?.metascore

            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}