package com.mok.moviespaginatedlist.data

import com.mok.moviespaginatedlist.app.ApiServices
import java.io.File
import kotlin.reflect.KClass


/**
 * Helper function which will load JSON from
 * the path specified
 *
 * @param path : Path of JSON file
 * @return json : JSON from file at given path
 */
fun getJson(relativePath: String, myClass: KClass<ApiServices>): String {
    // Load the JSON response
    val path = "json/$relativePath"
    myClass.java.classLoader?.getResource(path)?.let {
        val file = File(it.path)
        return String(file.readBytes())
    }
    throw IllegalStateException("Resource not found")
}