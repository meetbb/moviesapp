package com.mr.moviesapp.ui.main.fragments

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mr.moviesapp.R
import com.mr.moviesapp.ui.main.MovieDetailsActivity
import com.mr.moviesapp.ui.main.`object`.MovieResults
import kotlinx.android.synthetic.main.movie_details.*


class MovieDetailFragment : Fragment() {

    val ARG_MOVIE = "ARG_MOVIE"
    val EXTRA_TRAILERS = "EXTRA_TRAILERS"
    val EXTRA_REVIEWS = "EXTRA_REVIEWS"
    private var mMovie: MovieResults? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!!.containsKey(ARG_MOVIE)) {
            mMovie = arguments!!.getParcelable<MovieResults>(ARG_MOVIE)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarLayout: CollapsingToolbarLayout = activity!!.findViewById(R.id.toolbar_layout)
        if (activity is MovieDetailsActivity) {
            appBarLayout.title = mMovie!!.original_title
        }
        val backDropImageView: ImageView = activity!!.findViewById(R.id.movie_backdrop)
        Glide.with(activity!!).load("http://image.tmdb.org/t/p/original/" + mMovie!!.backdrop_path)
            .apply(RequestOptions().placeholder(R.drawable.image_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(backDropImageView)

        movie_title.text = mMovie!!.original_title
        movie_overview.text = mMovie!!.overview
        movie_release_date.text = mMovie!!.release_date
        Glide.with(activity).load("http://image.tmdb.org/t/p/original/" + mMovie!!.poster_path)
            .apply(RequestOptions().placeholder(R.drawable.image_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(movie_poster)
    }
}