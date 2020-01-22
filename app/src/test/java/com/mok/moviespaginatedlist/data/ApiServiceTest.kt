package com.mok.moviespaginatedlist.data


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mok.moviespaginatedlist.app.ApiServices
import com.mok.moviespaginatedlist.data.mockWebServer.MockWebServerRule
import com.mok.moviespaginatedlist.di.TestKoinModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject


/**
 * This test are for testing the ApiService
 * **/
class ApiServiceTest : AutoCloseKoinTest() {

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @get:Rule
    var mockWebServer = MockWebServerRule()

    private val service: ApiServices by inject()

    @Before
    fun before() {
        startKoin {
            modules(TestKoinModules.getModules())
        }
    }

    @Test
    fun movieTest() {
        service.movies(1)
            .test()
            .await()
            .assertValue { it.size == 20 }
    }


}
