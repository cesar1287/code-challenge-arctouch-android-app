package com.arctouch.codechallenge.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.preference.MainPreference
import com.arctouch.codechallenge.util.KEY_EXTRA_QUERY
import com.arctouch.codechallenge.util.Status
import com.arctouch.codechallenge.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private var searchAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val query = intent.getStringExtra(KEY_EXTRA_QUERY)
        MainPreference.setUserReference(this, query)

        setupRecyclerView()
        loadContent()

        buttonRetry.setOnClickListener {
            loadContent()
        }
    }

    private fun loadContent() {
        val viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.moviePagedList?.observe(this, Observer { pagedList ->
            searchAdapter?.submitList(pagedList)
        })

        viewModel.resourceLiveDataSource?.observe(this, Observer { resource ->
            when (resource?.status) {
                Status.LOADING -> {
                    setVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE)
                }
                Status.ERROR -> {
                    errorMessage.text = resource.message

                    setVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE)
                }
                Status.SUCCESS -> {
                    setVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE)
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
        searchAdapter = SearchAdapter(this)
        recyclerView.adapter = searchAdapter
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
