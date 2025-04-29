package com.example.entrenet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AboutAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_about_app)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "About App"
        supportActionBar?.setDisplayHomeAsUpEnabled(false) // No back button
    }
}
