package com.mok.moviespaginatedlist.di

import org.koin.core.module.Module

class AppKoinModules {

    companion object {

        fun getModules(): List<Module> {
            return mutableListOf(appModule).apply {
                addAll(DomainKoinModules.getModules())
            }
        }
    }
}