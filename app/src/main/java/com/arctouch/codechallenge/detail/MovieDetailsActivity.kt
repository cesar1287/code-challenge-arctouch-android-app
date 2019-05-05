package com.arctouch.codechallenge.detail

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.ActivityMovieDetailsBinding
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.GlideApp
import com.arctouch.codechallenge.util.KEY_EXTRA_MOVIE
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie: Movie = intent.getParcelableExtra(KEY_EXTRA_MOVIE)
        val binding : ActivityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        binding.movie = movie

        genres.text = movie.genres?.joinToString(separator = ", ") { it.name }

        GlideApp.with(this)
                .load(movie.backdropPath?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(binding.cover)

        GlideApp.with(this)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(binding.poster)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
