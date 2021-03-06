package com.example.moviessearchengine.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviessearchengine.R
import com.example.moviessearchengine.model.Movie
import com.example.moviessearchengine.utils.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val itemClickListener: OnItemClickListener, private val context: Context?) :
    PagedListAdapter<Movie, MovieAdapter.ViewHolder>(movieDiff) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.tvTitle.text = movie!!.title
        holder.tvYear.text = movie.year
        holder.ivPoster.loadImage(movie.poster)
        context?.getColor(R.color.white_smoke)?.let { holder.itemView.setBackgroundColor(it) }
        holder.bind(movie, itemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.nameDetails
        val tvYear: TextView = view.releaseYear
        val ivPoster: ImageView = view.thumbnail

        fun bind(movie: Movie?, clickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                clickListener.onItemClicked(movie)
            }
        }
    }

    companion object {
        val movieDiff = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.title == newItem.title && oldItem.poster == newItem.poster && oldItem.year == newItem.year
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(movie: Movie?)
    }
}