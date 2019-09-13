package fragments


import adapters.CardsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import apps.android.fattahnexx103.apptract.R
import apps.android.fattahnexx103.apptract.activities.TinderCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.fragment_swipe.*
import util.DATA_GENDER
import util.UserData


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

        cardsAdapter = CardsAdapter(context,R.layout.item, rowItems )

        database.child(userId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(UserData::class.java)
                preferredGender = user?.preferredGender
                populateItems()
            }
        })

        swipe_Fling.adapter = cardsAdapter
        swipe_Fling.setFlingListener(object: SwipeFlingAdapterView.onFlingListener{
            override fun removeFirstObjectInAdapter() {

            }

            override fun onLeftCardExit(p0: Any?) {

            }

            override fun onRightCardExit(p0: Any?) {

            }

            override fun onAdapterAboutToEmpty(p0: Int) {

            }

            override fun onScroll(p0: Float) {

            }
        })

    }

    fun populateItems(){
        val cardsQuery = database.orderByChild(DATA_GENDER).equalTo(preferredGender)
        cardsQuery.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{child ->
                    var user = child.getValue(UserData::class.java)
                    if(user != null){
                        var showUser = false
                    }
                }
            }
        })
    }

    //    private var al = ArrayList<String>()
//    private var arrayAdapter: ArrayAdapter<String>? = null
//    private var i = 0


    //        al.add("php");
//        al.add("c");
//        al.add("python");
//        al.add("java");
//
//        //choose your favorite adapter
//        arrayAdapter = ArrayAdapter(this,
//            R.layout.item,
//            R.id.helloText, al);
//
//        //set the listener and the adapter
//        frame_swipe.setAdapter(arrayAdapter);
//        frame_swipe.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
//            override fun removeFirstObjectInAdapter() {
//                Log.d("LIST", "removed object!");
//                al.removeAt(0);
//                arrayAdapter?.notifyDataSetChanged();
//            }
//
//            override fun onLeftCardExit(p0: Any?) {
//                Toast.makeText(this@HomeActivity, "Left!", Toast.LENGTH_SHORT).show();
//            }
//
//            override fun onRightCardExit(p0: Any?) {
//                Toast.makeText(this@HomeActivity, "Right!", Toast.LENGTH_SHORT).show();
//            }
//
//            override fun onAdapterAboutToEmpty(p0: Int) {
//                al.add("XML  is $i");
//                arrayAdapter?.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
//            }
//
//            override fun onScroll(p0: Float) {
//
//            }
//
//        })


}
