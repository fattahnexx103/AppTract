package fragments


import adapters.ChatsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import apps.android.fattahnexx103.apptract.R
import apps.android.fattahnexx103.apptract.activities.TinderCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_matches.*
import util.Chat
import util.DATA_MATCHES
import util.UserData


class MatchesFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var database: DatabaseReference
    private lateinit var chatdatabase: DatabaseReference

    private val chatAdapter = ChatsAdapter(ArrayList()) //initiate the chats adapter

    private var tinderCallback: TinderCallback? = null

    fun setCallBack(tinderCallback : TinderCallback){
        this.tinderCallback = tinderCallback
        userId = tinderCallback.onGetUserID()
        database = tinderCallback.getUserDatabase()
        chatdatabase = tinderCallback.getChatDatabase()

        fetchData()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //configure recyler view
        matches_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }
    }


    fun fetchData(){
        //we go under the matches and those only have the user id of those who matched
        database.child(userId).child(DATA_MATCHES).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.hasChildren()){
                    p0.children.forEach{child ->
                        val matchId = child.key //we use matchId as the key
                        val chatId = child.value.toString() //we use the actual id to represent chat Ids
                        if(!matchId.isNullOrEmpty()){
                            database.child(matchId).addListenerForSingleValueEvent(object: ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {

                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    val user = p0.getValue(UserData::class.java)
                                    if(user != null){
                                        //make a new chat user
                                        val chat = Chat(userId, chatId, user.uid, user.name, user.imageUrl)
                                        chatAdapter.addElement(chat)
                                    }
                                }
                            })
                        }
                    }
                }
            }
        })
    }


}
