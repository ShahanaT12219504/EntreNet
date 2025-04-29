package com.example.entrenet

import GeminiRequest
import GeminiResponse
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ChatDetailActivity : AppCompatActivity() {

    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<MessageAdapter.MessageItem>()

    private var personalityName: String? = null

    private val apiKey = "AIzaSyCfjjedKE5d8Jt6tZZ6ZGmz1Swk5Iug2zM" // Use your valid key here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        personalityName = intent.getStringExtra("name")
        val initialMessage = intent.getStringExtra("message")
        val imageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_foreground)

        val nameTextView: TextView = findViewById(R.id.chat_name)
        val messageTextView: TextView = findViewById(R.id.chat_message)
        val imageView: ImageView = findViewById(R.id.chat_image)

        nameTextView.text = personalityName ?: "Personality"
        messageTextView.text = initialMessage ?: "No initial message"
        imageView.setImageResource(imageResId)

        recyclerView = findViewById(R.id.message_recycler_view)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_button)

        messageAdapter = MessageAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter

        sendButton.setOnClickListener {
            val userInput = messageEditText.text.toString().trim()
            if (userInput.isNotEmpty()) {
                // Add user's message immediately
                messages.add(MessageAdapter.MessageItem(userInput, true))
                messageAdapter.notifyItemInserted(messages.size - 1)
                recyclerView.scrollToPosition(messages.size - 1)

                // Clear the input field
                messageEditText.text.clear()

                // Delay adding the typing indicator
                lifecycleScope.launch {
                    delay(1000L) // 1000 ms = 1 second

                    val typingItem = MessageAdapter.MessageItem("$personalityName is typing...", false, isTyping = true)
                    messages.add(typingItem)
                    messageAdapter.notifyItemInserted(messages.size - 1)
                    recyclerView.scrollToPosition(messages.size - 1)

                    // Now send the message
                    sendMessageToGemini(userInput)
                }
            }
        }

    }

    private fun sendMessageToGemini(userMessage: String) {
        val prompt = "You are $personalityName. Respond as $personalityName to: \"$userMessage\""

        val request = GeminiRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt))))
        )

        val call = RetrofitClient.apiService.generateContent(apiKey, request)

        call.enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                // Remove typing indicator
                val typingIndex = messages.indexOfLast { it.isTyping }
                if (typingIndex != -1) {
                    messages.removeAt(typingIndex)
                    messageAdapter.notifyItemRemoved(typingIndex)
                }

                if (response.isSuccessful) {
                    val aiReply = response.body()?.candidates
                        ?.firstOrNull()
                        ?.content
                        ?.parts
                        ?.firstOrNull()
                        ?.text

                    if (!aiReply.isNullOrBlank()) {
                        messages.add(MessageAdapter.MessageItem(aiReply, isSent = false))
                    } else {
                        messages.add(MessageAdapter.MessageItem("Error: Empty response from Gemini.", isSent = false))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    messages.add(MessageAdapter.MessageItem("Error: API failed (${response.code()}) - $errorBody", isSent = false))
                }

                messageAdapter.notifyItemInserted(messages.size - 1)
                recyclerView.scrollToPosition(messages.size - 1)
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                val typingIndex = messages.indexOfLast { it.isTyping }
                if (typingIndex != -1) {
                    messages.removeAt(typingIndex)
                    messageAdapter.notifyItemRemoved(typingIndex)
                }

                messages.add(MessageAdapter.MessageItem("Error: Network failure - ${t.localizedMessage}", isSent = false))
                messageAdapter.notifyItemInserted(messages.size - 1)
                recyclerView.scrollToPosition(messages.size - 1)
            }
        })
    }
}
