package fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import apps.android.fattahnexx103.apptract.R
import apps.android.fattahnexx103.apptract.activities.TinderCallback
import com.google.firebase.database.DatabaseReference


class ProfileFragment : Fragment() {

    //lateInit means we will instantiate it before we use it
    private lateinit var userId: String
    private lateinit var dataBase: DatabaseReference
    private var tinderCallback: TinderCallback? = null

    fun setCallBack(tinderCallback :TinderCallback){
        this.tinderCallback = tinderCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


}
