package com.mok.moviespaginatedlist.data.mockWebServer

import com.mok.moviespaginatedlist.app.ApiServices
import com.mok.moviespaginatedlist.data.getJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class DispatcherMockWebServer : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val requestUrl = request.requestUrl

        return when (requestUrl?.encodedPath) {
            "discover/movie" -> {
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        getJson(
                            "movies/movie_page.json",
                            ApiServices::class
                        )
                    )
            }
            else -> MockResponse().setResponseCode(404)
        }


    }

}