package apps.android.fattahnexx103.apptract.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import apps.android.fattahnexx103.apptract.R

class ChatActivity : AppCompatActivity() {

    private var chatId : String? = null
    private var userId : String? = null
    private var imageUrl : String? = null
    private var otheruserId : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatId = intent.extras.getString(PARAM_CHAT_ID)
        userId = intent.extras.getString(PARAM_USER_ID)
        imageUrl = intent.extras.getString(PARAM_IMAGE_URL)
        otheruserId = intent.extras.getString(PARAM_OTHER_USER_ID)

        //check to see if any of them is empty..if so log error and finish
        if(chatId.isNullOrEmpty() || userId.isNullOrEmpty() || imageUrl.isNullOrEmpty() || otheruserId.isNullOrEmpty()){
            Toast.makeText(this, "Chat room initialization error", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun onSend(view: View){

    }

    companion object {

        private val PARAM_CHAT_ID = "Chat id"
        private val PARAM_USER_ID = "User id"
        private val PARAM_IMAGE_URL = "image url"
        private val PARAM_OTHER_USER_ID = "Other user id "

        fun newIntent(context: Context?, chatId: String?, userId: String?, imageUrl: String?, otheruserId: String?): Intent{

            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(PARAM_CHAT_ID, chatId)
            intent.putExtra(PARAM_IMAGE_URL, imageUrl)
            intent.putExtra(PARAM_USER_ID,userId)
            intent.putExtra(PARAM_OTHER_USER_ID,otheruserId)
            return intent
        }
    }
}
