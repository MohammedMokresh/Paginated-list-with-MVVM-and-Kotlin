package com.mok.moviespaginatedlist.di

import org.koin.core.module.Module

class DataKoinModules {

    companion object {
        fun getModules(): List<Module> {

            return listOf(cacheModule, repositoryModule, serviceModule)
        }
    }

}