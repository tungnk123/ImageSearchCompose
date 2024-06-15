package com.tungdoan.imagesearchcompose

import android.app.Application

class ImageSearchComposeApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}