package apps.android.fattahnexx103.apptract.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import apps.android.fattahnexx103.apptract.R
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_info.*
import util.DATA_USERS
import util.UserData

class UserInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val userId = intent.extras.getString(PARAM_USER_ID, "")
        if(userId.isNullOrEmpty()){
            finish()
        }

        val userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS)
        userDatabase.child(userId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(UserData::class.java)
                userInfo_name.text = user?.name
                userInfo_age.text = user?.age
                if(user?.imageUrl != null){
                    Glide.with(this@UserInfoActivity)
                        .load(user.imageUrl)
                        .into(userInfo_imageView)
                }
            }
        })
    }

    companion object{

        private val PARAM_USER_ID = "User Id"
        fun newIntent(context: Context, userId: String?): Intent {
            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtra(PARAM_USER_ID,userId)
            return intent
        }
    }
}
