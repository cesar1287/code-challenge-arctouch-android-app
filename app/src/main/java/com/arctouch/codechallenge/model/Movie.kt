package com.arctouch.codechallenge.model

import android.os.Parcelable
import android.support.v7.util.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String?,
    val genres: List<Genre>?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?
) : Parcelable {
    companion object {
        // use for ordering the items in view
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
