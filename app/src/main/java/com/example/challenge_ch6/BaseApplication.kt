package com.example.challenge_ch6

import android.app.Application
import com.example.common.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferences.init(this)
    }

}