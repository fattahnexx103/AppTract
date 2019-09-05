package apps.android.fattahnexx103.apptract.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import apps.android.fattahnexx103.apptract.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    //this function is called after user logs in
    fun onLogin(view: View){
        startActivity(MainActivity.newIntent(this))
    }

    //static function
    companion object {
        //if it returns only one thing
        fun newIntent(context: Context?) =  Intent(context, LoginActivity::class.java)
    }
}
