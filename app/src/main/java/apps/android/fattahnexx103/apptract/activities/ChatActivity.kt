package apps.android.fattahnexx103.apptract.activities

import adapters.MessagesAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apps.android.fattahnexx103.apptract.R
import com.bumptech.glide.Glide
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_matches.*
import util.DATA_CHATS
import util.DATA_MESSAGES
import util.Message
import util.UserData
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private var chatId : String? = null
    private var userId : String? = null
    private var imageUrl : String? = null
    private var otheruserId : String? = null

    private lateinit var  chatDatabase: DatabaseReference
    private lateinit var messagesAdapter: MessagesAdapter

    private val chatMessagesListener = object: ChildEventListener{
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {

        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val message = p0.getValue(Message::class.java)
            if(message != null){
                messagesAdapter.addMessage(message)
                chat_recyclerView.post{
                    chat_recyclerView.smoothScrollToPosition(messagesAdapter.itemCount - 1) //scroll when added
                }
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatId = intent.extras.getString(PARAM_CHAT_ID)
        userId = intent.extras.getString(PARAM_USER_ID)
        imageUrl = intent.extras.getString(PARAM_IMAGE_URL)
        otheruserId = intent.extras.getString(PARAM_OTHER_USER_ID)

        //check to see if any of them is empty..if so log error and finish
        if(chatId.isNullOrEmpty() || userId.isNullOrEmpty() || imageUrl.isNullOrEmpty() || otheruserId.isNullOrEmpty()){
            Toast.makeText(this, "Chat room initialization error", Toast.LENGTH_SHORT).show()
            finish()
        }

        chatDatabase = FirebaseDatabase.getInstance().reference.child(DATA_CHATS)
        messagesAdapter = MessagesAdapter(ArrayList(), userId!!)
        chat_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = messagesAdapter
        }

        chatDatabase.child(chatId!!).child(DATA_MESSAGES).addChildEventListener(chatMessagesListener)

        chatDatabase.child(chatId!!).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{value ->
                    val key = value.key
                    val user = value.getValue(UserData::class.java)
                    if(!key.equals(userId)){
                        chat_title_name.text = user?.name
                        Glide.with(this@ChatActivity)
                            .load(user?.imageUrl)
                            .into(chat_imageView)

                        chat_imageView.setOnClickListener {
                            startActivity(UserInfoActivity.newIntent(this@ChatActivity,otheruserId))
                        }
                    }
                }
            }

        })

    }

    fun onSend(view: View){
        val message = Message(userId,chat_message_editText.text.toString(), Calendar.getInstance().time.toString())
        val key = chatDatabase.child(chatId!!).child(DATA_MESSAGES).push().key
        if(!key.isNullOrEmpty()){
            chatDatabase.child(chatId!!).child(DATA_MESSAGES).child(key).setValue(message)
        }
        chat_message_editText.setText("",TextView.BufferType.EDITABLE)
    }

    companion object {

        private val PARAM_CHAT_ID = "Chat id"
        private val PARAM_USER_ID = "User id"
        private val PARAM_IMAGE_URL = "image url"
        private val PARAM_OTHER_USER_ID = "Other user id "

        fun newIntent(context: Context?, chatId: String?, userId: String?, imageUrl: String?, otheruserId: String?): Intent{

            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(PARAM_CHAT_ID, chatId)
            intent.putExtra(PARAM_IMAGE_URL, imageUrl)
            intent.putExtra(PARAM_USER_ID,userId)
            intent.putExtra(PARAM_OTHER_USER_ID,otheruserId)
            return intent
        }
    }
}
