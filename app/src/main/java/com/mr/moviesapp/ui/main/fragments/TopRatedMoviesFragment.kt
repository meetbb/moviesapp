package com.mr.moviesapp.ui.main.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.mr.moviesapp.MainActivity
import com.mr.moviesapp.R
import com.mr.moviesapp.ui.main.MovieDetailsActivity
import com.mr.moviesapp.ui.main.`object`.MovieResults
import com.mr.moviesapp.ui.main.adapter.TopRatedMoviesAdapter
import com.mr.moviesapp.ui.main.utils.*
import kotlinx.android.synthetic.main.fragment_movies.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.IOException


class TopRatedMoviesFragment : Fragment(), MovieClickListener, FavClickListener {
    val backDropBitmap: Bitmap? = null
    val posterBitmp: Bitmap? = null
    override fun onFavClick(movieResults: MovieResults, position: Int) {
        loadingProgressBar.visibility = View.VISIBLE
        doAsync {
            val backDropBitmap =
                Glide.with(activity).asBitmap().load("http://image.tmdb.org/t/p/original/" + movieResults.backdrop_path)
                    .submit().get()
            val posterBitmap =
                Glide.with(activity).asBitmap().load("http://image.tmdb.org/t/p/original/" + movieResults.poster_path)
                    .submit()
                    .get()
            uiThread {
                loadingProgressBar.visibility = View.INVISIBLE
                val databaseHelper: DatabaseHelper = DatabaseHelper(activity!!)
                val status = databaseHelper.addFavorite(
                    movieResults,
                    getByteArrayFrmBitmap(backDropBitmap!!)!!,
                    getByteArrayFrmBitmap(posterBitmap!!)!!
                )
                if (status > -1) {
                    Toast.makeText(activity, "record save", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun showMovieDetails(movie: MovieResults, position: Int) {
        (activity as MainActivity).let {
            val intent = Intent(it, MovieDetailsActivity::class.java)
            intent.putExtra("ARG_MOVIE", movie)
            it.startActivity(intent)
        }
    }

    private val client = OkHttpClient()
    var topRatedMoviesList: List<MovieResults> = emptyList()
    var topRatedMoviesAdapter: TopRatedMoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar.visibility = View.INVISIBLE
        moviesRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        moviesRecyclerView.hasFixedSize()
        topRatedMoviesAdapter = TopRatedMoviesAdapter(false, topRatedMoviesList, this, this)
        moviesRecyclerView.adapter = topRatedMoviesAdapter
        requestTopRatedMovies()
    }

    fun requestTopRatedMovies() {
        if (isNetworkAvailable(activity!!)) {
            loadingProgressBar.visibility = View.VISIBLE
            callAPI("https://api.themoviedb.org/3/movie/top_rated?api_key=" + resources.getString(R.string.API_KEY))
        } else {

        }
    }

    fun resetAdapter(updatedList: List<MovieResults>) {
        activity!!.runOnUiThread({
            topRatedMoviesAdapter!!.updateData(updatedList)
            topRatedMoviesAdapter!!.notifyDataSetChanged()
        })
    }

    fun callAPI(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                loadingProgressBar.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call, response: Response) {
                loadingProgressBar.visibility = View.INVISIBLE
                topRatedMoviesList = parseMoviesFromJson(response.body()?.string())
                resetAdapter(topRatedMoviesList)
            }
        })
    }

    fun parseMoviesFromJson(jsonStr: String?): List<MovieResults> {
        val jsonObj = JSONObject(jsonStr!!.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf("}") + 1))
        val resultsJson = jsonObj.getJSONArray("results")
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(resultsJson.toString(), Array<MovieResults>::class.java).toList()
    }
}