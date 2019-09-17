package fragments


import adapters.CardsAdapter
import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

import apps.android.fattahnexx103.apptract.R
import apps.android.fattahnexx103.apptract.activities.TinderCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.fragment_swipe.*
import util.*


class SwipeFragment : Fragment() {

    private var tinderCallback: TinderCallback? = null
    private lateinit var userId: String
    private lateinit var database: DatabaseReference
    private var cardsAdapter: ArrayAdapter<UserData>? =null
    private var rowItems = ArrayList<UserData>()
    private var preferredGender: String? = null

    fun setCallBack(tinderCallback : TinderCallback){
        this.tinderCallback = tinderCallback
        userId = tinderCallback.onGetUserID()
        database = tinderCallback.getUserDatabase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database.child(userId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(UserData::class.java)
                preferredGender = user?.preferredGender
                populateItems()
            }
        })

        cardsAdapter = CardsAdapter(context,R.layout.item, rowItems )


        frame.adapter = cardsAdapter
        frame.setFlingListener(object: SwipeFlingAdapterView.onFlingListener{
            override fun removeFirstObjectInAdapter() {
                rowItems.removeAt(0)
                cardsAdapter?.notifyDataSetChanged()
            }

            override fun onLeftCardExit(p0: Any?) {
                var user = p0 as UserData //cast p0 to UserData object
                database.child(user.uid.toString()).child(DATA_SWIPES_LEFT).child(userId).setValue(true)
                //add the uid of the frame user under DataSwipesLeft property of current user
            }

            override fun onRightCardExit(p0: Any?) {
                val selectedUser = p0 as UserData
                val selectedUserId = selectedUser.uid

                if(!selectedUserId.isNullOrEmpty()){
                    database.child(userId).child(DATA_SWIPES_RIGHT)
                        .addListenerForSingleValueEvent(object: ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                               if(p0.hasChild(selectedUserId)){ //we check if the other user already swiped on this person
                                   Toast.makeText(context, "MATCH", Toast.LENGTH_SHORT).show()
                                   database.child(userId).child(DATA_SWIPES_RIGHT).child(selectedUserId).removeValue()
                                   database.child(userId).child(DATA_MATCHES).child(selectedUserId).setValue(true)
                                   database.child(selectedUserId).child(DATA_MATCHES).child(userId).setValue(true)
                               }else{
                                   database.child(selectedUserId).child(DATA_SWIPES_RIGHT).child(userId).setValue(true)
                               }
                            }
                        })
                }

            }


            override fun onAdapterAboutToEmpty(p0: Int) {

            }

            override fun onScroll(p0: Float) {

            }
        })

        frame.setOnItemClickListener{position, data ->

        }

        likeButton.setOnClickListener {
            if(!rowItems.isEmpty()){
                frame.topCardListener.selectRight()
            }
        }

        dislikeButton.setOnClickListener {
            if(!rowItems.isEmpty()){
                frame.topCardListener.selectLeft()
            }
        }


    }

    fun populateItems(){
        noUsersLayout.visibility = View.GONE
        progressLayout.visibility = View.VISIBLE
        val cardsQuery = database.orderByChild(DATA_GENDER).equalTo(preferredGender) //we query the database
        cardsQuery.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{child ->
                    var user = child.getValue(UserData::class.java) //get the user
                    if(user != null){ //if there is a user
                        var showUser = true  //have been user
                        if(child.child(DATA_SWIPES_LEFT).hasChild(userId)
                            || child.child(DATA_SWIPES_RIGHT).hasChild(userId)
                            || child.child(DATA_MATCHES).hasChild(userId)
                        ){
                            showUser = false
                        }
                        if(showUser){
                            rowItems.add(user)
                            cardsAdapter?.notifyDataSetChanged()
                        }
                    }
                }
                progressLayout.visibility = View.GONE
                if(rowItems.isEmpty()){ //if there are no users
                    noUsersLayout.visibility = View.VISIBLE
                }
            }
        })
    }



}
