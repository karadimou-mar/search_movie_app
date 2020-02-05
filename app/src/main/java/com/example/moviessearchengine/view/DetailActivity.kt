package com.example.moviessearchengine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.moviessearchengine.R
import com.example.moviessearchengine.utils.loadImage
import com.example.moviessearchengine.utils.loadPoster
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_movie.textView_title

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getIntentExtras()
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
}
