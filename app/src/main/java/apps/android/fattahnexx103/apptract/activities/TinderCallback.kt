package apps.android.fattahnexx103.apptract.activities

import com.google.firebase.database.DatabaseReference

interface TinderCallback {

    fun onSignOut()

    fun onGetUserID(): String //will return string

    fun getUserDatabase() : DatabaseReference //will gi ve database reference

    fun profileComplete()
}