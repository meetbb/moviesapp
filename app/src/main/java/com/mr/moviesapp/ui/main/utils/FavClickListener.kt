package com.mr.moviesapp.ui.main.utils

import com.mr.moviesapp.ui.main.`object`.MovieResults


interface FavClickListener {
    fun onFavClick(movieResults: MovieResults, position: Int)
}