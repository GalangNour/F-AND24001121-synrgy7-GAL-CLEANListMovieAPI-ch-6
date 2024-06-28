package com.example.challenge_ch6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.perf.FirebasePerformance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myTrace = FirebasePerformance.getInstance().newTrace("myTrace")

        myTrace.start()

        myTrace.stop()
    }
}