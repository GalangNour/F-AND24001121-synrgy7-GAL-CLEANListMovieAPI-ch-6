package com.example.common

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferences {
    private lateinit var sharedPreferences: SharedPreferences
    private const val PREFS_NAME = "PREFS_NAME"

    const val USERNAME = "USERNAME"

    fun init(context : Context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }


    var username: String
        set(value) {
            sharedPreferences.edit {
                putString(USERNAME, value)
            }
        }
        get() = sharedPreferences.getString(USERNAME, "") ?: ""

}