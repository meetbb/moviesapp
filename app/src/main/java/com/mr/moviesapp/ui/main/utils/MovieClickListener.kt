package com.mr.moviesapp.ui.main.utils

import com.mr.moviesapp.ui.main.`object`.MovieResults


interface MovieClickListener {
    fun showMovieDetails(movie: MovieResults, position: Int)
}