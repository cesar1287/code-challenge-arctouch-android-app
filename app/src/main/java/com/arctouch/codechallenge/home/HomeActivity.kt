package com.arctouch.codechallenge.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.Status
import com.arctouch.codechallenge.viewmodel.UpcomingMoviesViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    private var homeAdapter: HomeAdapter? = null
    private val moviesList: MutableList<Movie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupRecyclerView()
        loadContent()

        buttonRetry.setOnClickListener {
            loadContent()
        }

//        api.upcomingMovies(API_KEY, DEFAULT_LANGUAGE, 1, DEFAULT_REGION)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                val moviesWithGenres = it.results.map { movie ->
//                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
//                }
//                recyclerView.adapter = HomeAdapter(moviesWithGenres)
//                progressBar.visibility = View.GONE
//            }
    }

    private fun loadContent() {
        val viewModel = ViewModelProviders.of(this).get(UpcomingMoviesViewModel::class.java)
        viewModel.getUpcomingMovies()?.observe(this, Observer { resource ->
            when(resource?.status) {
                Status.LOADING -> {
                    setVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE)
                }
                Status.ERROR -> {
                    errorMessage.text = resource.message

                    setVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE)
                }
                Status.SUCCESS -> {
                    resource.data?.let { moviesListNonNull ->
                        moviesList.addAll(moviesListNonNull.results)
                        homeAdapter?.notifyDataSetChanged()

                        setVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE)
                    } ?: run {
                        setVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE)
                    }
                }
            }
        })
    }

    private fun setVisibility(progress: Int, recycler: Int, noContent: Int, error: Int) {
        progressBar.visibility = progress
        contentLayout.visibility = recycler
        no_content.visibility = noContent
        errorLayout.visibility = error
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        homeAdapter = HomeAdapter(moviesList)
        recyclerView.adapter = homeAdapter
    }
}
