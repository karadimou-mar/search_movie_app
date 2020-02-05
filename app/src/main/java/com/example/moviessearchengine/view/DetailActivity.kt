package com.example.moviessearchengine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.moviessearchengine.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getIntentExtras()
    }

    private fun getIntentExtras() {
        val bundle: Bundle? = intent.extras
        val title = bundle?.getString("title")
        setIntentExtras(title)

    }

    private fun setIntentExtras(title: String?) {
         val titleT: TextView = findViewById(R.id.textView_title)
         titleT.text = title
    }
}
