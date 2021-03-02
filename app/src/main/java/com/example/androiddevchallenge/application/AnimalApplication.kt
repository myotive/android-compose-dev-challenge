package com.example.androiddevchallenge.application

import android.app.Application
import timber.log.Timber

import timber.log.Timber.DebugTree

class AnimalApplication() : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }
}