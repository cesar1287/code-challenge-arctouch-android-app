package com.arctouch.codechallenge.home

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.search.SearchActivity
import com.arctouch.codechallenge.util.KEY_EXTRA_QUERY
import com.arctouch.codechallenge.util.Status
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
        viewModel.moviePagedList?.observe(this, Observer { pagedList ->
            homeAdapter?.submitList(pagedList)
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
        homeAdapter = HomeAdapter(this)
        recyclerView.adapter = homeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        setupSearchView(menu)

        return true
    }

    private fun setupSearchView(menu: Menu) {
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.action_search).actionView as SearchView)
        searchView.apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }

        setupQueryTextListener(searchView)
    }

    private fun setupQueryTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { queryNonNull ->
                    val intent = Intent(this@HomeActivity, SearchActivity::class.java)
                    intent.putExtra(KEY_EXTRA_QUERY, queryNonNull)
                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }
}
