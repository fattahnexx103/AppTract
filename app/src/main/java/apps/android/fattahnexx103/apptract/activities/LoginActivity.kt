package apps.android.fattahnexx103.apptract.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import apps.android.fattahnexx103.apptract.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private  val firebaseAuthListener = FirebaseAuth.AuthStateListener {

        val user = firebaseAuth.currentUser

        if(user != null){
            startActivity(MainActivity.newIntent(this))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    //this function is called after user clicks login button
    fun onLogin(view: View){
        if(!editText_email.text.toString().isNullOrEmpty() && !editText_password.text.toString().isNullOrEmpty()){
            firebaseAuth.signInWithEmailAndPassword(editText_email.text.toString(), editText_password.text.toString())
                .addOnCompleteListener { task ->
                    if(!task.isSuccessful){
                        Toast.makeText(this, "Sign In Error .. {${task.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    //static function
    companion object {
        //if it returns only one thing
        fun newIntent(context: Context?) =  Intent(context, LoginActivity::class.java)
    }
}
