package com.mr.moviesapp.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mr.moviesapp.R
import com.mr.moviesapp.ui.main.fragments.FavouriteMoviesFragment
import com.mr.moviesapp.ui.main.fragments.TopRatedMoviesFragment

private val TAB_TITLES = arrayOf(
    R.string.top_rated_string,
    R.string.favourites_string
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TopRatedMoviesFragment()
            1 -> FavouriteMoviesFragment()
            else -> TopRatedMoviesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}