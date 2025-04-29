package com.example.entrenet

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val messages: List<MessageItem>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    data class MessageItem(
        val message: String,
        val isSent: Boolean,
        val isTyping: Boolean = false
    )

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.text_message_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layout = if (viewType == 1) R.layout.item_message_sent else R.layout.item_message_received
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messageItem = messages[position]
        holder.messageText.text = messageItem.message

        if (messageItem.isTyping) {
            holder.messageText.setTypeface(null, Typeface.ITALIC)
            holder.messageText.setTextColor(Color.GRAY)
        } else {
            holder.messageText.setTypeface(null, Typeface.NORMAL)
            holder.messageText.setTextColor(
                if (messageItem.isSent) Color.WHITE else Color.BLACK
            )
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSent) 1 else 0
    }
}
