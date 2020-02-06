package com.example.moviessearchengine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.MovieDetail
import com.example.moviessearchengine.network.MovieAPIClient
import com.example.moviessearchengine.utils.loadImage
import com.example.moviessearchengine.utils.loadPoster
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_movie.textView_title
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO: landscape layout

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getIntentExtras()

        //TODO: getMovieDetails with id
        getMovieDetails(textView_title.text.toString())
    }

    private fun getIntentExtras() {
        val bundle: Bundle? = intent.extras
        val title = bundle?.getString("title")
        val poster: String? = bundle?.getString("poster")
        setIntentExtras(title,poster)

    }

    private fun setIntentExtras(title: String?, poster: String?) {
//         val titleT: TextView = findViewById(R.id.textView_title)
//         titleT.text = title
        textView_title.text = title
        imageView_poster.loadPoster(poster)

    }

    private fun getMovieDetails(title: String){
        val call: Call<MovieDetail> = MovieAPIClient.getMovieDetails(title)
        call.enqueue(object: Callback<MovieDetail> {
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                t.printStackTrace()
                Log.e("getMovieDetails FAILED", t.message!!)
            }

            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                val resp: MovieDetail? = response.body()
                Log.d("BOOM",""+resp)
                textView_plot.text = resp?.plot

            }

        })
    }
}
