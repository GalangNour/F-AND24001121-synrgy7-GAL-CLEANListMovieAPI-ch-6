package com.example.challenge_ch6.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_ch6.databinding.ItemMovieBinding
import com.example.domain.model.MovieDetail

class AdapterMovie(private val movieDetailList: List<MovieDetail>?, private val itemClickListener: OnNoteItemClickListener) : RecyclerView.Adapter<AdapterMovie.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieDetailList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieDetailList?.get(position)
        if (movie != null) {
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(movie)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieDetail) {
            binding.apply {
                tvTitle.text = movie.title
                tvOverview.text = movie.overview
                Glide.with(imgMovie)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .into(imgMovie)
            }
        }
    }

    interface OnNoteItemClickListener {
        fun onItemClick(movieDetail: MovieDetail)
    }
}
