package com.example.entrenet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Paint

class ChecklistAdapter(private val items: MutableList<ChecklistItem>) :
    RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {

    class ChecklistViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checklist, parent, false)
        return ChecklistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        val item = items[position]
        holder.checkBox.text = item.task
        holder.checkBox.isChecked = item.isChecked

        // Apply or remove strikethrough
        if (item.isChecked) {
            holder.checkBox.paintFlags = holder.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.checkBox.paintFlags = holder.checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked

            // Update visual state
            if (isChecked) {
                holder.checkBox.paintFlags = holder.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                // Delay then remove item from list
                holder.view.postDelayed({
                    (items as MutableList).removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, items.size)
                }, 500)
            } else {
                holder.checkBox.paintFlags = holder.checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
