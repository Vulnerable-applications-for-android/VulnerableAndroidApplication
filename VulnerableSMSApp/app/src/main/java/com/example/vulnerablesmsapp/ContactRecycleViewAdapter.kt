package com.example.vulnerablesmsapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.messages_list_item.view.*

class ContactRecycleViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages: List<ContactData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.messages_list_item, parent, false)
        return MessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MessagesViewHolder -> {
                holder.bind(messages[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun submitList(contactList: List<ContactData>) {
        messages = contactList
    }

    class MessagesViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val name = itemView.name_text_view

        fun bind(contactData: ContactData) {
            name.text = contactData.name
            itemView.setOnClickListener {
                val intent = Intent(it.context, MessageActivity::class.java)
                intent.putExtra("name", contactData.name)
                intent.putExtra("id", contactData.id)
                intent.putExtra("number", contactData.number)
                startActivity(it.context, intent, null)
            }
        }

    }


}