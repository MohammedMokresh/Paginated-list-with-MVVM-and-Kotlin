package com.mok.moviespaginatedlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mok.moviespaginatedlist.R
import com.mok.moviespaginatedlist.databinding.ActivityMainBinding
import com.mok.moviespaginatedlist.genres.GenreViewModel
import com.mok.moviespaginatedlist.languages.LanguagesViewModel
import com.mok.moviespaginatedlist.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MoviesViewModel by viewModel()

    private val genreViewModel: GenreViewModel by viewModel()

    private val languagesViewModel: LanguagesViewModel by viewModel()

    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)
    }

    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAdapter()

        genreViewModel.insertGenresInLocal()
        languagesViewModel.callAndSaveLanguages()


        binding.movieListSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPage().observe(this, Observer { it.refreshPage() })
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

                if (it.status == Status.SUCCESS) {
                    binding.movieListSwipeRefreshLayout.isRefreshing = false
                }

                Timber.d("Received a new NetworkState STATUS ---- ${it.status} Message  ----- ${it.msg}")
                adapter.setNetworkState(it)
            })


    }

    @Suppress("SENSELESS_COMPARISON")
    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager != null) {


            val trans =
                supportFragmentManager.beginTransaction()
            trans.remove(MovieDetailsFragment.newInstance(0))
            trans.commit()
            supportFragmentManager.popBackStack()

        }

    }

}
