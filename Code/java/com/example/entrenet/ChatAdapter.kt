package com.example.entrenet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val chatList: List<ChatItem>,
    private val onItemClick: (ChatItem) -> Unit
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val messageText: TextView = itemView.findViewById(R.id.messageText)
        val profileImage: ShapeableImageView = itemView.findViewById(R.id.profileImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatItem = chatList[position]
        holder.nameText.text = chatItem.name
        holder.messageText.text = chatItem.message
        holder.profileImage.setImageResource(chatItem.imageResId)

        holder.itemView.setOnClickListener {
            onItemClick(chatItem)
        }
    }

    override fun getItemCount() = chatList.size
}
