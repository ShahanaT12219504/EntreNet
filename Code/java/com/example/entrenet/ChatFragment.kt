package com.example.entrenet

import com.example.entrenet.ChatAdapter
import com.example.entrenet.ChatItem
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private lateinit var chatList: List<ChatItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerView = view.findViewById(R.id.chat_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        chatList = listOf(
            ChatItem("Elon Musk", "Let’s connect over Mars colonization!", R.drawable.elon),
            ChatItem("Jeff Bezos", "Exploring new markets!", R.drawable.bezos),
            ChatItem("Mark Zuckerberg", "Meta updates coming soon.", R.drawable.zuck),
            ChatItem("Sundar Pichai", "Excited about AI at Google.", R.drawable.pichai),
            ChatItem("Tim Cook", "Stay hungry, stay foolish.", R.drawable.cook),
            ChatItem("Mukesh Ambani", "Let’s build something meaningful.", R.drawable.mukesh)
        )

        adapter = ChatAdapter(chatList) { chatItem ->
            val intent = Intent(requireContext(), ChatDetailActivity::class.java).apply {
                putExtra("name", chatItem.name)
                putExtra("message", chatItem.message)
                putExtra("imageResId", chatItem.imageResId)
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        return view
    }
}
