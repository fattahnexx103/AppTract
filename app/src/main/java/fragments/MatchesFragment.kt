package fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import apps.android.fattahnexx103.apptract.R
import apps.android.fattahnexx103.apptract.activities.TinderCallback
import com.google.firebase.database.DatabaseReference


class MatchesFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var database: DatabaseReference
    private lateinit var chatdatabase: DatabaseReference

    private var tinderCallback: TinderCallback? = null

    fun setCallBack(tinderCallback : TinderCallback){
        this.tinderCallback = tinderCallback
        userId = tinderCallback.onGetUserID()
        database = tinderCallback.getUserDatabase()
        chatdatabase = tinderCallback.getChatDatabase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }


}
