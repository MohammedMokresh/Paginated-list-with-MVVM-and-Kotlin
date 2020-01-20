package com.mok.moviespaginatedlist.data.mockWebServer

import com.mok.moviespaginatedlist.BuildConfig
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import okhttp3.mockwebserver.MockWebServer
import timber.log.Timber
import java.io.IOException
import java.lang.Exception


class MockWebServerRule : TestRule {

    companion object {
        const val MOCK_WEBSERVER_PORT = 8000
        const val MOCK_WEB_SERVER_URL = BuildConfig.BASE_URL
    }

    private lateinit var mServer: MockWebServer

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                startServer()
                try {
                    base?.evaluate()
                } finally {
                    stopServer()
                }
            }
        }
    }


    fun server(): MockWebServer {
        return mServer
    }

    fun startServer() {
        mServer = MockWebServer()
        try {
            mServer.start(MOCK_WEBSERVER_PORT)
            mServer.dispatcher =
                DispatcherMockWebServer()
        } catch (e: Exception) {
            Timber.e(e, "mock server start issue")
        }

    }

    fun stopServer() {
        try {
            mServer.shutdown();
        } catch (e: IOException) {
            Timber.e(e, "mock server shutdown error")
        }
    }


}