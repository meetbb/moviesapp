package com.mr.moviesapp.ui.main.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.mr.moviesapp.ui.main.`object`.MovieResults


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "FavoriteMovies.db"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "Favourite_Movies"
        val KEY_ID = "MOVIE_ID"
        val KEY_BACKDROP = "backdrop_path"
        val KEY_ORG_LANG = "original_language"
        val KEY_ORG_TITLE = "original_title"
        val KEY_OVERVIEW = "overview"
        val KEY_POPULARITY = "popularity"
        val KEY_POSTER = "poster_path"
        val KEY_REL_DATE = "release_date"
        val KEY_TITLE = "title"
        val KEY_VOTE_AVG = "vote_average"
        val KEY_VOTE_CONT = "vote_count"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BACKDROP + " BLOB," + KEY_ORG_LANG + " TEXT," + KEY_ORG_TITLE + " TEXT,"
                + KEY_OVERVIEW + " TEXT,"
                + KEY_POPULARITY + " TEXT,"
                + KEY_POSTER + " BLOB,"
                + KEY_REL_DATE + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_VOTE_AVG + " TEXT,"
                + KEY_VOTE_CONT + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addFavorite(movieResults: MovieResults, backDropImage: ByteArray, posterImage: ByteArray): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, movieResults.id)
        contentValues.put(KEY_ORG_LANG, movieResults.original_language)
        contentValues.put(KEY_ORG_TITLE, movieResults.original_title)
        contentValues.put(KEY_OVERVIEW, movieResults.overview)
        contentValues.put(KEY_POPULARITY, movieResults.popularity)
        contentValues.put(KEY_REL_DATE, movieResults.release_date)
        contentValues.put(KEY_TITLE, movieResults.title)
        contentValues.put(KEY_VOTE_AVG, movieResults.vote_average)
        contentValues.put(KEY_VOTE_CONT, movieResults.vote_count)
        contentValues.put(KEY_BACKDROP, backDropImage)
        contentValues.put(KEY_POSTER, posterImage)
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    fun removeFavorite(movieResults: MovieResults): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, movieResults.id)
        val success = db.delete(TABLE_NAME, KEY_ID + "=" + movieResults.id, null)
        db.close() // Closing database connection
        return success
    }

    fun getFavoriteMovie(): List<MovieResults> {
        val empList: ArrayList<MovieResults> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var movieId: Int
        var orgLang: String
        var orgTitle: String
        var overView: String
        var popularity: String
        var releaseDate: String
        var title: String
        var avgVote: String
        var voteCount: String
        var backDrop: ByteArray
        var poster: ByteArray
        if (cursor.moveToFirst()) {
            do {
                movieId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                orgLang = cursor.getString(cursor.getColumnIndex(KEY_ORG_LANG))
                orgTitle = cursor.getString(cursor.getColumnIndex(KEY_ORG_TITLE))
                overView = cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW))
                popularity = cursor.getString(cursor.getColumnIndex(KEY_POPULARITY))
                releaseDate = cursor.getString(cursor.getColumnIndex(KEY_REL_DATE))
                title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                avgVote = cursor.getString(cursor.getColumnIndex(KEY_VOTE_AVG))
                voteCount = cursor.getString(cursor.getColumnIndex(KEY_VOTE_CONT))
                backDrop = cursor.getBlob(cursor.getColumnIndex(KEY_BACKDROP))
                poster = cursor.getBlob(cursor.getColumnIndex(KEY_POSTER))
                val emp = MovieResults(
                    true,
                    backdrop_path = backDrop.toString(),
                    id = movieId,
                    original_language = orgLang,
                    original_title = orgTitle,
                    overview = overView,
                    popularity = popularity.toDouble(),
                    release_date = releaseDate,
                    title = title,
                    vote_average = avgVote.toDouble(),
                    vote_count = voteCount.toInt(),
                    poster_path = poster.toString()
                )
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
}