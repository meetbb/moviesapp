package com.mr.moviesapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mr.moviesapp.R
import com.mr.moviesapp.ui.main.`object`.MovieResults
import com.mr.moviesapp.ui.main.utils.FavClickListener
import com.mr.moviesapp.ui.main.utils.MovieClickListener
import com.mr.moviesapp.ui.main.utils.getImageFromByte


class TopRatedMoviesAdapter(
    private var isFromFavourites: Boolean,
    private var list: List<MovieResults>,
    private var movieClickListener: MovieClickListener, private var favClickListener: FavClickListener
) :
    RecyclerView.Adapter<TopRatedMoviesAdapter.MoviesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        return MoviesHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie: MovieResults = list[position]
        holder.itemView.setOnClickListener { v -> movieClickListener.showMovieDetails(movie, position) }
        return holder.bind(movie, favClickListener, position, isFromFavourites)
    }

    fun updateData(updatedList: List<MovieResults>) {
        list = updatedList
    }


    class MoviesHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.movie_item_cell, parent, false)) {

        private var movieTitle: TextView? = null
        private var moviePoster: ImageView? = null
        public var isFavourite: ImageView? = null

        init {
            movieTitle = itemView.findViewById(R.id.movie_title)
            moviePoster = itemView.findViewById(R.id.movie_thumbnail)
            isFavourite = itemView.findViewById(R.id.favouriteIcon)
        }

        fun bind(movie: MovieResults, favClickListener: FavClickListener, position: Int, isFromFavourites: Boolean) {
            movieTitle?.text = movie.original_title
            isFavourite?.setOnClickListener { v -> favClickListener.onFavClick(movie, position) }
            if (isFromFavourites) {
                moviePoster?.setImageBitmap(getImageFromByte(movie.poster_path!!.toByteArray(Charsets.UTF_8)))
            } else {
                Glide.with(itemView.context).load("http://image.tmdb.org/t/p/original/" + movie.poster_path)
                    .apply(
                        RequestOptions().placeholder(R.drawable.image_placeholder).diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                    )
                    .into(moviePoster)
            }
        }
    }
}