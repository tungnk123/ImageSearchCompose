package com.tungdoan.imagesearchcompose

import android.app.Application

class ImageSearchComposeApplication: Application() {
    private lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}