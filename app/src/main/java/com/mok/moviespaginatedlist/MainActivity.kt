package com.mok.moviespaginatedlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mok.moviespaginatedlist.ui.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private val viewModel: MoviesViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
