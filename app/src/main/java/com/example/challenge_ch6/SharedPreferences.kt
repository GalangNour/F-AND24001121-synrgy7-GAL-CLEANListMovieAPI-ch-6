package com.example.challenge_ch6

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferences {
    private lateinit var sharedPreferences: SharedPreferences
    private const val PREFS_NAME = "PREFS_NAME"

    const val ISLOGIN = "IS_LOGIN"
    const val USERNAME = "USERNAME"

    fun init(context : Context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var isLogin: Boolean
        set(value) {
            sharedPreferences.edit {
                putBoolean(ISLOGIN, value)
            }
        }
        get() = sharedPreferences.getBoolean(ISLOGIN, false)

    var username: String
        set(value) {
            sharedPreferences.edit {
                putString(USERNAME, value)
            }
        }
        get() = sharedPreferences.getString(USERNAME, "") ?: ""

}