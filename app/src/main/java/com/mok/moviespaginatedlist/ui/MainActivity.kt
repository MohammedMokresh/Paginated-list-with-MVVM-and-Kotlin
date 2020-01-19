package com.mok.moviespaginatedlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mok.moviespaginatedlist.R
import com.mok.moviespaginatedlist.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MoviesViewModel by viewModel()

    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)
    }

    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAdapter()


        binding.movieListSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPage().observe(this, Observer { it.refreshPage() })
            binding.movieListSwipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onStop() {
        super.onStop()

        viewModel.deleteAllExceptfirstTen()
    }

    private fun initAdapter() {
        binding.moviesRecyclerView.layoutManager = layoutManager
        binding.moviesRecyclerView.adapter = adapter
        binding.moviesRecyclerView.setHasFixedSize(true)

        viewModel.dataSource
            .observe(this, Observer {
                adapter.submitList(it)
            })

        viewModel.networkState
            .observe(this, Observer {
                Timber.d("Received a new NetworkState STATUS ---- ${it.status} Message  ----- ${it.msg}")
                adapter.setNetworkState(it)
            })


    }

}
