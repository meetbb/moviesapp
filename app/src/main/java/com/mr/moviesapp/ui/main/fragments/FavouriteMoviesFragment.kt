package com.mr.moviesapp.ui.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mr.moviesapp.R
import com.mr.moviesapp.ui.main.`object`.MovieResults
import com.mr.moviesapp.ui.main.adapter.TopRatedMoviesAdapter
import com.mr.moviesapp.ui.main.utils.DatabaseHelper
import com.mr.moviesapp.ui.main.utils.FavClickListener
import com.mr.moviesapp.ui.main.utils.MovieClickListener
import kotlinx.android.synthetic.main.fragment_movies.*


class FavouriteMoviesFragment : Fragment(), MovieClickListener, FavClickListener {
    override fun onFavClick(movieResults: MovieResults, position: Int) {

    }

    override fun showMovieDetails(movie: MovieResults, position: Int) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar.visibility = View.INVISIBLE
        moviesRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        moviesRecyclerView.hasFixedSize()
        val databaseHelper: DatabaseHelper = DatabaseHelper(activity!!)
        val topRatedMoviesAdapter = TopRatedMoviesAdapter(true, databaseHelper.getFavoriteMovie(), this, this)
        moviesRecyclerView.adapter = topRatedMoviesAdapter
    }
}