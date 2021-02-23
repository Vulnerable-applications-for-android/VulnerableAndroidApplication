package com.example.vulnerablesmsapp

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_list_item.view.*
import kotlinx.android.synthetic.main.messages_list_item.view.*

class MessageRecycleViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages: List<MessageData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_list_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MessageViewHolder -> {
                holder.bind(messages[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun submitList(messageList: List<MessageData>) {
        messages = messageList
    }

    class MessageViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val message = itemView.message_text_view
        private val messageBreaker = itemView.message_breaker

        fun bind(messageData: MessageData) {
            message.text = messageData.message
            if (messageData.isUser == 1) {
                messageBreaker.setBackgroundColor(Color.parseColor("#00FF00"))
            } else {
                messageBreaker.setBackgroundColor(Color.parseColor("#0000FF"))
            }
        }
    }
}