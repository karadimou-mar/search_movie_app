package com.example.moviessearchengine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.MovieAdapter
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.utils.ListDecorationPadding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_movies)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val heroes = ArrayList<Movie>()

        heroes.add(Movie("Spiderman", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))
        heroes.add(Movie("Spiderman", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))
        heroes.add(Movie("Wolverine", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))
        heroes.add(Movie("Spiderman", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))
        heroes.add(Movie("PPPPPPPPP", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))
        heroes.add(Movie("Spiderman", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))
        heroes.add(Movie("Spiderman", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))
        heroes.add(Movie("Spiderman", "https://cdn3.whatculture.com/images/2019/07/c425c3a37288d808-600x338.jpg"))

        val adapter = MovieAdapter(heroes)

        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            ListDecorationPadding(this, 0, 0)

        )
    }
    }

