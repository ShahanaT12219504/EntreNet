package com.example.entrenet

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.entrenet.ui.FindFragment
import com.example.entrenet.ui.ReminderFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Load saved theme before setting content view
        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val savedThemeIndex = preferences.getInt("theme_index", 2)
        applyTheme(savedThemeIndex)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Logo Click - Opens EntreNet Website
        val ivLogo: ImageView = findViewById(R.id.ivLogo)
        ivLogo.setOnClickListener {
            val websiteUrl = "https://www.entrenet.in"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        // Setup Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_checklist -> loadFragment(ChecklistFragment())

                R.id.nav_announcement -> {
                    startActivity(Intent(this, AnnouncementActivity::class.java))
                }

                R.id.nav_theme -> showThemePicker() // Theme Picker Dialog

                R.id.nav_about -> {
                    startActivity(Intent(this, AboutUsActivity::class.java))
                }

                R.id.nav_about_app -> {
                    startActivity(Intent(this, AboutAppActivity::class.java))
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        // Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        loadFragment(HomeFragment()) // Load Home by default

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_find -> loadFragment(FindFragment())
                R.id.nav_explore -> loadFragment(ExploreFragment())
                R.id.nav_reminder -> loadFragment(ReminderFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())

            }
            true
        }

        // Floating Chat Button - Opens ChatFragment instead of Chatbot
        val fabChatbot: FloatingActionButton = findViewById(R.id.fab_chatbot)
        fabChatbot.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, ChatFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun showThemePicker() {
        val themes = arrayOf("Light", "Dark", "System Default")
        val savedThemeIndex = preferences.getInt("theme_index", 2)

        AlertDialog.Builder(this)
            .setTitle("Choose App Theme")
            .setSingleChoiceItems(themes, savedThemeIndex) { dialog, which ->
                preferences.edit().putInt("theme_index", which).apply()
                applyTheme(which)
                dialog.dismiss()
                recreate()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun applyTheme(index: Int) {
        when (index) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        val fabChatbot = findViewById<FloatingActionButton>(R.id.fab_chatbot)
        if (fragment is ChecklistFragment) {
            fabChatbot.hide()
        } else {
            fabChatbot.show()
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
