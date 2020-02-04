package com.example.moviessearchengine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.MovieAdapter
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.network.MovieAPIClient
import com.example.moviessearchengine.utils.ListDecorationPadding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        button_search.setOnClickListener {
            getMovies(editText_search.text.toString())
        }

    }

    private fun getMovies(title: String) {
        val call: Call<Movie> = MovieAPIClient.getMovies(title)
        call.enqueue(object: Callback<Movie>{
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED MOVIE API CONN", t.message)
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val resp: Movie? = response.body()
                Log.d("response", "" + response)

            }

        })
    }
}

