package com.mok.moviespaginatedlist

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mok.moviespaginatedlist.databinding.ActivityBookTicketsBinding

class BookTicketsActivity : AppCompatActivity() {

    var binding: ActivityBookTicketsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_tickets)

        binding?.bannerWebView?.loadUrl("https://www.cathaycineplexes.com.sg/")


        // Enable Javascript
        val webSettings = binding?.bannerWebView?.settings
        webSettings?.javaScriptEnabled = true


        // Force links and redirects to open in the WebView instead of in a browser
        binding?.bannerWebView?.webViewClient = WebViewClient()


    }
}
