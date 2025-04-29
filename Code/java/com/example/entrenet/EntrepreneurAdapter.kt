// com/example/entrenet/adapter/EntrepreneurAdapter.kt
package com.example.entrenet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.entrenet.R
import com.example.entrenet.model.Entrepreneur

class EntrepreneurAdapter(
    private var entrepreneurList: List<Entrepreneur>,
    private val onSelectionChanged: (List<Entrepreneur>) -> Unit
) : RecyclerView.Adapter<EntrepreneurAdapter.EntrepreneurViewHolder>() {

    private var filteredList: List<Entrepreneur> = entrepreneurList

    inner class EntrepreneurViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        val skillsTextView: TextView = itemView.findViewById(R.id.tvSkills)
        val placeTextView: TextView = itemView.findViewById(R.id.tvPlace)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrepreneurViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entrepreneur, parent, false)
        return EntrepreneurViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntrepreneurViewHolder, position: Int) {
        val entrepreneur = filteredList[position]
        holder.nameTextView.text = entrepreneur.name
        holder.skillsTextView.text = entrepreneur.skills.joinToString(", ")
        holder.placeTextView.text = entrepreneur.place
        holder.checkBox.isChecked = entrepreneur.isSelected

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            entrepreneur.isSelected = isChecked
            onSelectionChanged(getSelectedEntrepreneurs())
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filterBySkill(skill: String) {
        filteredList = if (skill == "All") {
            entrepreneurList
        } else {
            entrepreneurList.filter { it.skills.contains(skill) }
        }
        notifyDataSetChanged()
    }

    fun getSelectedEntrepreneurs(): List<Entrepreneur> {
        return entrepreneurList.filter { it.isSelected }
    }
}
