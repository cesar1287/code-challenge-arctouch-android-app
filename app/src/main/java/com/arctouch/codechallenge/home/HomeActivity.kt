package com.arctouch.codechallenge.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.viewmodel.UpcomingMoviesViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    private var homeAdapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupRecyclerView()
        loadContent()

        buttonRetry.setOnClickListener {
            loadContent()
        }
    }

    private fun loadContent() {
        val viewModel = ViewModelProviders.of(this).get(UpcomingMoviesViewModel::class.java)
        viewModel.itemPagedList?.observe(this, Observer { pagedList ->
            homeAdapter?.submitList(pagedList)
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
        homeAdapter = HomeAdapter()
        recyclerView.adapter = homeAdapter
    }
}
