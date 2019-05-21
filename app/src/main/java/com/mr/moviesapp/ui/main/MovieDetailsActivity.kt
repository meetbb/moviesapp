package com.mr.moviesapp.ui.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mr.moviesapp.R
import com.mr.moviesapp.ui.main.fragments.MovieDetailFragment
import kotlinx.android.synthetic.main.layout_movie_detail.*


class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_movie_detail)
        setSupportActionBar(detail_toolbar)
        checkBuildVersion()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            val arguments = Bundle()
            arguments.putParcelable("ARG_MOVIE", intent.getParcelableExtra("ARG_MOVIE"))
            val fragment = MovieDetailFragment()
            fragment.setArguments(arguments)
            supportFragmentManager.beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit()
        }
    }

    protected fun checkBuildVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}