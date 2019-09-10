package apps.android.fattahnexx103.apptract.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TableLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import apps.android.fattahnexx103.apptract.R
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import fragments.MatchesFragment
import fragments.ProfileFragment
import fragments.SwipeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import util.DATA_IMAGE_URL
import util.DATA_USERS
import java.io.ByteArrayOutputStream
import java.io.IOException

const val REQUEST_CODE_PHOTO = 11

class HomeActivity : AppCompatActivity(), TinderCallback {

    //declare the fragments (initially we set them to null)
    private var profileFragment: ProfileFragment? = null
    private var swipeFragment: SwipeFragment? = null
    private var matchesFragment: MatchesFragment? = null
    private var resultImageUrl: Uri? = null

    //create 3 tabs (Initially we set them to null)
    private var profileTab: TabLayout.Tab? = null
    private var  swipeTab: TabLayout.Tab? = null
    private var matchesTab: TabLayout.Tab? = null

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userId = firebaseAuth.currentUser?.uid //user id  may be null
    private lateinit var userDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check whther user is there
        if(userId.isNullOrEmpty()){
            onSignOut() //signOut since there is no user
        }

        userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS) //get a ref to database

        //create the tabs in the tablayout
        profileTab = home_tabLay.newTab()
        swipeTab = home_tabLay.newTab()
        matchesTab = home_tabLay.newTab()

        //attach the tab icons
        profileTab?.icon = ContextCompat.getDrawable(this, R.drawable.tab_profile)
        swipeTab?.icon = ContextCompat.getDrawable(this, R.drawable.tab_swipe)
        matchesTab?.icon = ContextCompat.getDrawable(this, R.drawable.tab_matches)

        //finally add the tabs
        home_tabLay.addTab(profileTab!!) //!! we are sure that it is not going to be null
        home_tabLay.addTab(swipeTab!!)
        home_tabLay.addTab(matchesTab!!)

        home_tabLay.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabSelected(tab)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab){ //switch statement of the tab
                    profileTab ->{ //if tab is profileTab
                        //if its not created
                        if(profileFragment == null){
                            profileFragment = ProfileFragment()
                            profileFragment!!.setCallBack(this@HomeActivity) //setting the callback to pass data into the fragment
                        }
                        replaceFragment(profileFragment!!)
                    }
                    swipeTab ->{
                        if(swipeFragment == null){
                            swipeFragment = SwipeFragment()
                            swipeFragment!!.setCallBack(this@HomeActivity)
                        }
                        replaceFragment(swipeFragment!!)

                    }
                    matchesTab ->{
                        if(matchesFragment == null){
                            matchesFragment = MatchesFragment()
                            swipeFragment!!.setCallBack(this@HomeActivity)
                        }
                        replaceFragment(matchesFragment!!)
                    }
                }
            }

        })

        //set profileTab as first one selected
        profileTab?.select()

    }
    //calls fragment manager to put the fragments layout in the fragment container
    fun replaceFragment(fragment: Fragment){
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.home_linearLay, fragment) //replace the fragment in the layout with passed in fragment
            transaction.commit()
    }

    override fun onSignOut() {
        //sign out the user
        firebaseAuth.signOut()
        startActivity(StartActivity.newIntent(this)) //redirect to startup activity
        finish()
    }

    override fun onGetUserID(): String {
        return userId!!
    }

    override fun getUserDatabase(): DatabaseReference {
        return userDatabase!!
    }

    override fun profileComplete() {
        swipeTab?.select() //this means swipe to the next tab
    }

    override fun startActivityforPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*" //we want image of any type
        startActivityForResult(intent, REQUEST_CODE_PHOTO) //start the activity
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO){
            resultImageUrl = data?.data
            storeImage()
        }
    }



    fun storeImage(){
        //send image to firebase storage
        if(resultImageUrl != null && userId != null){
            val filePath = FirebaseStorage.getInstance().reference.child("profileImage").child(userId)
            var bitmap :Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(application.contentResolver, resultImageUrl)
            }catch (e: IOException){
                e.printStackTrace()
            }

            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG,20,baos)
            val data = baos.toByteArray()

            val uploadTask = filePath.putBytes(data)
            uploadTask.addOnFailureListener{e -> e.printStackTrace()}
            uploadTask.addOnSuccessListener { taskSnapshot ->
                filePath.downloadUrl
                    .addOnSuccessListener { uri ->
                        profileFragment?.updateImageUri(uri.toString())
                    }
                    .addOnFailureListener{e ->
                        e.printStackTrace()
                    }
            }
        }
    }



    //static function
    companion object {
        //if it returns only one thing
        fun newIntent(context: Context?) =  Intent(context, HomeActivity::class.java)
    }


}
