package com.mok.moviespaginatedlist.di

import org.koin.core.module.Module

class DomainKoinModules {

    companion object {
        fun getModules(): List<Module> {
            return mutableListOf(viewModelModule).apply {
                addAll(DataKoinModules.getModules())
            }
        }
    }
}