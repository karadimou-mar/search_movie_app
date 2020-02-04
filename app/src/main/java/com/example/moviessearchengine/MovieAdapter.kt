package com.example.moviessearchengine

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter (private val movie: ArrayList<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = movie[position].title
        holder.tvYear.text = movie[position].year
        holder.itemView.setBackgroundColor(Color.WHITE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false))
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvTitle: TextView = view.textView_title
        val tvYear:TextView = view.textView_year
    }
}