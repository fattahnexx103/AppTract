package apps.android.fattahnexx103.apptract.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import apps.android.fattahnexx103.apptract.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun onLogIn(view: View){
        startActivity(LoginActivity.newIntent(this))
    }

    fun onSignup(view: View){
        startActivity(SignUpActivity.newIntent(this))
    }

    //static function
    companion object {
        //if it returns only one thing
        fun newIntent(context: Context?) =  Intent(context, StartActivity::class.java)
    }
}
