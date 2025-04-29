package com.example.entrenet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AnnouncementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        // Set up the toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_announcement)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Announcements"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }


}
