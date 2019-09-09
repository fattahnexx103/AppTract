package fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import apps.android.fattahnexx103.apptract.R
import apps.android.fattahnexx103.apptract.activities.TinderCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.*
import util.*


class ProfileFragment : Fragment() {

    //lateInit means we will instantiate it before we use it
    private lateinit var userId: String
    private lateinit var dataBase: DatabaseReference
    private var tinderCallback: TinderCallback? = null


    fun setCallBack(tinderCallback :TinderCallback){
        this.tinderCallback = tinderCallback
        userId = tinderCallback.onGetUserID() //reference to the user
        dataBase = tinderCallback.getUserDatabase().child(userId) //reference to the user details
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_progressBar_ll.setOnTouchListener{view, event-> true} //this has returned true so we dont want the progressbar layout

        populateInfo()

        profile_apply_btn.setOnClickListener{onApply()} //call the onApply function inside the onCLick
        profile_signout_btn.setOnClickListener{tinderCallback?.onSignOut()}
    }

    fun populateInfo(){
        profile_progressBar_ll.visibility = View.VISIBLE
        dataBase.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                profile_progressBar_ll.visibility = View.GONE
            }

            override fun onDataChange(dataSnap: DataSnapshot) {
                if(isAdded){
                    val user = dataSnap.getValue(UserData::class.java) //make the user object

                    //set the textViews with the data
                    profile_name.setText(user?.name, TextView.BufferType.EDITABLE)
                    profile_age.setText(user?.age, TextView.BufferType.EDITABLE)
                    profile_email.setText(user?.email, TextView.BufferType.EDITABLE)

                    //handle thr radio inputs
                    if(user?.gender == GENDER_MALE){
                        radio_btn_man.isChecked =true
                    }
                    if(user?.gender == GENDER_FEMALE){
                        radio_btn_woman.isChecked =true
                    }
                    if(user?.preferredGender == GENDER_MALE){
                        radio_btn_preffered_man.isChecked = true
                    }
                    if(user?.preferredGender == GENDER_FEMALE){
                        radio_btn_preffered_woman.isChecked =true
                    }
                    profile_progressBar_ll.visibility = View.GONE
                }
            }
        })
    }

    fun onApply(){
        if(profile_name.text.toString().isNullOrEmpty()
            || profile_email.text.toString().isNullOrEmpty()
            || profile_gender_rgroup.checkedRadioButtonId == -1 // -1 means not selected
            || profile_preferred_gender_rgroup.checkedRadioButtonId == -1){

            Toast.makeText(context, "Please complete Profile to Continue ",Toast.LENGTH_SHORT).show()
        }
        else{
            val name = profile_name.text.toString()
            val email = profile_email.text.toString()
            val age = profile_age.text.toString()

            val gender: String
            if(radio_btn_man.isChecked)
                { gender = GENDER_MALE}
            else{
                gender = GENDER_FEMALE
            }

            val preferredGender: String
            if(radio_btn_preffered_man.isChecked){
                preferredGender = GENDER_MALE
            }else{
                preferredGender = GENDER_FEMALE
            }

            //updateValues to database
            dataBase.child(DATA_NAME).setValue(name)
            dataBase.child(DATA_EMAIL).setValue(email)
            dataBase.child(DATA_AGE).setValue(age)
            dataBase.child(DATA_GENDER).setValue(gender)
            dataBase.child(DATA_PREFERRED_GENDER).setValue(preferredGender)

            tinderCallback?.profileComplete() //marking profile complete
        }
    }



}
