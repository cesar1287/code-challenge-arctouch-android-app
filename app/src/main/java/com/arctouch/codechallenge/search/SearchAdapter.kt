package com.arctouch.codechallenge.search

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.detail.MovieDetailsActivity
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.GlideApp
import com.arctouch.codechallenge.util.KEY_EXTRA_MOVIE
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class SearchAdapter(private var context: Context) : PagedListAdapter<Movie, SearchAdapter.ViewHolder>(Movie.DIFF_CALLBACK) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(context: Context, movie: Movie?) {
            itemView.titleTextView.text = movie?.title
            itemView.genresTextView.text = movie?.genres?.joinToString(separator = ", ") { it.name }
            itemView.releaseDateTextView.text = movie?.releaseDate

            GlideApp.with(itemView)
                .load(movie?.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)

            itemView.contentLayout.setOnClickListener {
                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra(KEY_EXTRA_MOVIE, movie)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(context, movie)
    }
}
