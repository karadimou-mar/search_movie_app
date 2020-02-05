package com.example.moviessearchengine

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.utils.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter (private val movie: ArrayList<Movie>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = movie[position].title
        holder.tvYear.text = movie[position].year
        holder.ivPoster.loadImage(movie[position].poster)
        holder.itemView.setBackgroundColor(Color.WHITE)
        holder.bind(movie[position],itemClickListener)
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
        val ivPoster: ImageView = view.imageView_thumbnail

        fun bind(movie: Movie?, clickListener: OnItemClickListener){
            itemView.setOnClickListener {
                clickListener.onItemClicked(movie)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(movie: Movie?)
    }
}