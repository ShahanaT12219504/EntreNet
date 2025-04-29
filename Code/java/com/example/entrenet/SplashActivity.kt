package com.example.entrenet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // This shows the logo + app name

        // Display splash screen for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Transition to LoginActivity after 2 seconds
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000) // 2 seconds delay
    }
}
