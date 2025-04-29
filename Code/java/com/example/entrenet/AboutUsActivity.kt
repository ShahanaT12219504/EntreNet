package com.example.entrenet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_about_us)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "About Us"
        supportActionBar?.setDisplayHomeAsUpEnabled(false) // No back button
    }
}
