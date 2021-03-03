package com.example.moviessearchengine.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.MovieDetail
import com.example.moviessearchengine.network.api.MovieAPIClient
import com.example.moviessearchengine.utils.loadPoster
import com.example.moviessearchengine.utils.logD
import com.example.moviessearchengine.utils.logE
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.nameDetails
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
        getMovieDetails(nameDetails.text.toString())
    }

    private fun getIntentExtras() {
        val bundle: Bundle? = intent.extras
        val title = bundle?.getString("title")
        val poster: String? = bundle?.getString("poster")
        setIntentExtras(title, poster)
    }

    private fun setIntentExtras(title: String?, poster: String?) {
        nameDetails.text = title
        imageView_poster.loadPoster(poster)
    }

    private fun getMovieDetails(title: String) {
        val call: Call<MovieDetail> = MovieAPIClient.getMovieDetails(title)
        call.enqueue(object : Callback<MovieDetail> {
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                t.printStackTrace()
                logE("getMovieDetails FAILED", t)
            }

            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                val resp: MovieDetail? = response.body()
                logD("onResponse: $resp")
                plot.text = resp?.plot
                director.text = resp?.director
                writer.text = resp?.writer
                starring.text = resp?.actor
                //use resource strings with placeholders
                details.text = getString(
                    R.string.movie_details_slash_value,
                    resp?.rated,
                    resp?.runtime,
                    resp?.genre,
                    resp?.released
                )
                imdbRating.text = resp?.imdbRating
                for (i in 0 until resp?.rating!!.size) {
                    if (resp.rating[i].source.isNullOrEmpty())
                        rtRating.text = getString(R.string.dummy_rating)
                     else
                        rtRating.text = resp.rating[i].value
                }
                metaRating.text = resp.metascore
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
