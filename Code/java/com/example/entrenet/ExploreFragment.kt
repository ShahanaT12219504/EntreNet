package com.example.entrenet

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class ExploreFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var resultsContainer: LinearLayout
    private lateinit var recentSearchContainer: LinearLayout
    private lateinit var clearAllButton: Button

    private val searchResults = mapOf(
        "elon musk" to "https://www.tesla.com/",
        "spacex" to "https://www.spacex.com/",
        "jeff bezos" to "https://www.blueorigin.com/",
        "amazon startup" to "https://www.amazon.com/",
        "mark zuckerberg" to "https://about.fb.com/",
        "meta platforms" to "https://about.meta.com/",
        "startup funding" to "https://www.ycombinator.com/",
        "venture capital" to "https://www.sequoiacap.com/",
        "openai" to "https://openai.com/",
        "artificial intelligence startup" to "https://deepmind.com/",
        "entrepreneurship courses" to "https://www.coursera.org/courses?query=entrepreneurship",
        "business incubator" to "https://www.techstars.com/",
        "small business grants" to "https://www.sba.gov/",
        "steve jobs" to "https://www.apple.com/",
        "business networking" to "https://www.linkedin.com/",
        "jane doe entrepreneur" to "https://www.forbes.com/"
    )

    private val sharedPref by lazy { requireActivity().getSharedPreferences("RecentSearches", Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        val btnImage1 = view.findViewById<ImageButton>(R.id.btnImage1)
        val btnImage2 = view.findViewById<ImageButton>(R.id.btnImage2)
        val btnImage3 = view.findViewById<ImageButton>(R.id.btnImage3)
        val btnImage4 = view.findViewById<ImageButton>(R.id.btnImage4)

// Set implicit intents for each button
        btnImage1.setOnClickListener {
            openUrl("https://www.forbes.com/entrepreneurs/")
        }

        btnImage2.setOnClickListener {
            openUrl("https://www.inc.com/")
        }

        btnImage3.setOnClickListener {
            openUrl("https://www.cnbc.com/world/?region=world")
        }

        btnImage4.setOnClickListener {
            openUrl("https://www.under30ceo.com/")
        }




        searchEditText = view.findViewById(R.id.searchEditText)
        resultsContainer = view.findViewById(R.id.resultsContainer)
        recentSearchContainer = view.findViewById(R.id.recentSearchContainer)
        clearAllButton = view.findViewById(R.id.clearAllButton)

        loadRecentSearches()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                if (query.isEmpty()) {
                    resultsContainer.removeAllViews()
                } else {
                    displaySearchResults(query)
                }
            }
        })

        clearAllButton.setOnClickListener {
            sharedPref.edit().clear().apply()
            loadRecentSearches()
        }

        return view
    }


    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


    private fun displaySearchResults(query: String) {
        resultsContainer.removeAllViews()

        searchResults.forEach { (key, url) ->
            if (key.contains(query, ignoreCase = true)) {
                val resultView = TextView(requireContext()).apply {
                    text = "> $key"
                    textSize = 16f
                    setPadding(16, 16, 16, 16)
                    setOnClickListener {
                        saveRecentSearch(key)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }
                }
                resultsContainer.addView(resultView)
            }
        }
    }

    private fun saveRecentSearch(search: String) {
        val recentSearches = sharedPref.getStringSet("searches", mutableSetOf())!!.toMutableSet()
        recentSearches.add(search)
        sharedPref.edit().putStringSet("searches", recentSearches).apply()
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        recentSearchContainer.removeAllViews()
        val recentSearches = sharedPref.getStringSet("searches", setOf())!!

        if (recentSearches.isEmpty()) {
            clearAllButton.visibility = View.GONE
        } else {
            clearAllButton.visibility = View.VISIBLE
            recentSearches.forEach { search ->
                val recentView = TextView(requireContext()).apply {
                    text = search
                    textSize = 14f
                    setPadding(16, 8, 16, 8)
                    setOnClickListener { searchEditText.setText(search) }
                }
                val removeButton = TextView(requireContext()).apply {
                    text = "❌"
                    textSize = 14f
                    setPadding(8, 8, 8, 8)
                    setOnClickListener {
                        removeRecentSearch(search)
                    }
                }
                val row = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.HORIZONTAL
                    addView(recentView)
                    addView(removeButton)
                }
                recentSearchContainer.addView(row)
            }
        }
    }

    private fun removeRecentSearch(search: String) {
        val recentSearches = sharedPref.getStringSet("searches", mutableSetOf())!!.toMutableSet()
        recentSearches.remove(search)
        sharedPref.edit().putStringSet("searches", recentSearches).apply()
        loadRecentSearches()
    }
}