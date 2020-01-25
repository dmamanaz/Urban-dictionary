package com.example.nikeurbandictionary

import android.app.Application
import android.content.Context

class UrbanDictionaryApplication : Application() {
    companion object {
        lateinit var instance: UrbanDictionaryApplication
        val context: Context get() = instance
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}