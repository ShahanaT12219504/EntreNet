// com/example/entrenet/ui/FindFragment.kt
package com.example.entrenet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.entrenet.R
import com.example.entrenet.adapter.EntrepreneurAdapter
import com.example.entrenet.databinding.FragmentFindBinding
import com.example.entrenet.model.Entrepreneur

class FindFragment : Fragment() {

    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EntrepreneurAdapter

    private val skills = listOf(
        "All",
        "Leadership",
        "Business Management",
        "Time Management",
        "People Management",
        "Communication",
        "Active Listening",
        "Networking",
        "Financial Management",
        "Problem-Solving",
        "Critical Thinking",
        "Sales",
        "Marketing",
        "Innovation",
        "Adaptability & Resilience",
        "Risk Management"
    )

    private val entrepreneurs = listOf(
        Entrepreneur("Aisha Sharma", listOf("Leadership", "Marketing"), "Mumbai"),
        Entrepreneur("Ravi Verma", listOf("Sales", "Problem-Solving"), "Delhi"),
        Entrepreneur("Neha Mehta", listOf("Financial Management", "Critical Thinking"), "Bengaluru"),
        Entrepreneur("Karan Malhotra", listOf("Time Management", "Networking"), "Chennai"),
        Entrepreneur("Pooja Joshi", listOf("Business Management", "Communication"), "Hyderabad"),
        Entrepreneur("Rohan Kapoor", listOf("Innovation", "Leadership"), "Kolkata"),
        Entrepreneur("Sneha Rao", listOf("Risk Management", "Adaptability & Resilience"), "Ahmedabad"),
        Entrepreneur("Aryan Gupta", listOf("Active Listening", "People Management"), "Pune"),
        Entrepreneur("Meera Desai", listOf("Sales", "Marketing", "Networking"), "Jaipur"),
        Entrepreneur("Devika Iyer", listOf("Critical Thinking", "Communication"), "Lucknow"),
        Entrepreneur("Tanmay Shah", listOf("Financial Management", "Problem-Solving"), "Bhopal"),
        Entrepreneur("Ishita Sen", listOf("Leadership", "People Management"), "Guwahati"),
        Entrepreneur("Rajeev Menon", listOf("Innovation", "Time Management"), "Thiruvananthapuram"),
        Entrepreneur("Tanya Paul", listOf("Business Management", "Risk Management"), "Patna"),
        Entrepreneur("Siddharth Jain", listOf("Adaptability & Resilience", "Sales"), "Nagpur")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up Spinner
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, skills)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSkill.adapter = spinnerAdapter

        // Set up RecyclerView and Adapter
        adapter = EntrepreneurAdapter(entrepreneurs.toMutableList()) { selectedList ->
            val selectedNames = selectedList.joinToString("\n") { it.name }
            binding.selectedTextBox.text = selectedNames.ifEmpty { "No one selected yet." }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Handle Spinner selection
        binding.spinnerSkill.setOnItemSelectedListener { _, _, position, _ ->
            val selectedSkill = skills[position]
            adapter.filterBySkill(selectedSkill)
        }
    }

    private fun Spinner.setOnItemSelectedListener(onItemSelected: (adapter: AdapterView<*>, view: View?, position: Int, id: Long) -> Unit) {
        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) = onItemSelected(parent, view, position, id)

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
