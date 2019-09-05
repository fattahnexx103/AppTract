package apps.android.fattahnexx103.apptract.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import apps.android.fattahnexx103.apptract.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {

        //check user
        val user = firebaseAuth.currentUser
        if(user != null){
            //this is in listener because this always checks in the status so user can auto log in
            startActivity(MainActivity.newIntent(this)) //redirects back to Mainactivity
            finish() //finish current activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener) //set the auth listener at startup
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener) //remove the listener
    }

    fun onSignup(view: View){
        //if the email and and pass are null or empty
        if(!editText_email.text.toString().isNullOrEmpty() && !editText_password.text.toString().isNullOrEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(editText_email.text.toString(),editText_password.text.toString()) //create the firebase user
                .addOnCompleteListener {task ->
                    if(!task.isSuccessful){
                        //if task is not successful then give an error
                        Toast.makeText(this, "Sign up Failed .. ${task.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    //static function
    companion object {
        //if it returns only one thing
        fun newIntent(context: Context?) =  Intent(context, SignUpActivity::class.java)
    }
}
