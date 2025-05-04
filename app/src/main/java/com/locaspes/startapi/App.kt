package com.locaspes.startapi

import android.app.Application
import com.locaspes.data.cloudinary.CloudinaryConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CloudinaryConfig.initialize(this)
    }
}