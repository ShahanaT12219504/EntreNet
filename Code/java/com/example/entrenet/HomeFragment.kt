package com.example.entrenet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Get Full Name from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val fullName = sharedPreferences.getString("fullName", "User") // Default: "User"

        // Find and Update TextView
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        tvTitle.text = "Welcome, $fullName!"

        return view
    }

}
