package com.example.entrenet

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChecklistFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChecklistAdapter
    private val checklistItems = mutableListOf<ChecklistItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_checklist, container, false)

        recyclerView = view.findViewById(R.id.checklistRecyclerView)
        adapter = ChecklistAdapter(checklistItems)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val fab: FloatingActionButton = view.findViewById(R.id.fab_add)
        fab.setOnClickListener {
            showAddItemDialog()
        }

        return view
    }

    private fun showAddItemDialog() {
        val editText = EditText(requireContext())
        editText.hint = "Enter your task"

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Task")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val task = editText.text.toString()
                if (task.isNotBlank()) {
                    checklistItems.add(ChecklistItem(task))
                    adapter.notifyItemInserted(checklistItems.size - 1)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
