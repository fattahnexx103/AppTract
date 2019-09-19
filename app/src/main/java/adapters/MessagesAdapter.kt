package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.android.fattahnexx103.apptract.R
import util.Message

class MessagesAdapter(private var messages: ArrayList<Message>, val userId: String):
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    companion object {
        val MESSAGE_CURRENT_USER = 1
        val MESSAGE_OTHER_USER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if(viewType == MESSAGE_CURRENT_USER){
            return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_current_user_message,parent,false))
        }else{
            return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_other_user_message,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        if(messages[position].sendBy.equals(userId)){
            return MESSAGE_CURRENT_USER
        }else{
            return MESSAGE_OTHER_USER
        }
    }

    class MessageViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bind(message: Message){
            view.findViewById<TextView>(R.id.chat_name_textView).text = message.message
        }
    }
}