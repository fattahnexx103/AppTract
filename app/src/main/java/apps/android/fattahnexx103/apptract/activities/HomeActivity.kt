package apps.android.fattahnexx103.apptract.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TableLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import apps.android.fattahnexx103.apptract.R
import com.google.android.material.tabs.TabLayout
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import fragments.MatchesFragment
import fragments.ProfileFragment
import fragments.SwipeFragment
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    //declare the fragments
    private var profileFragment: ProfileFragment? = null
    private var swipeFragment: SwipeFragment? = null
    private var matchesFragment: MatchesFragment? = null

    //create 3 tabs
    private var profileTab: TabLayout.Tab? = null
    private var  swipeTab: TabLayout.Tab? = null
    private var matchesTab: TabLayout.Tab? = null

//    private var al = ArrayList<String>()
//    private var arrayAdapter: ArrayAdapter<String>? = null
//    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

//        al.add("php");
//        al.add("c");
//        al.add("python");
//        al.add("java");
//
//        //choose your favorite adapter
//        arrayAdapter = ArrayAdapter(this,
//            R.layout.item,
//            R.id.helloText, al);
//
//        //set the listener and the adapter
//        frame_swipe.setAdapter(arrayAdapter);
//        frame_swipe.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
//            override fun removeFirstObjectInAdapter() {
//                Log.d("LIST", "removed object!");
//                al.removeAt(0);
//                arrayAdapter?.notifyDataSetChanged();
//            }
//
//            override fun onLeftCardExit(p0: Any?) {
//                Toast.makeText(this@HomeActivity, "Left!", Toast.LENGTH_SHORT).show();
//            }
//
//            override fun onRightCardExit(p0: Any?) {
//                Toast.makeText(this@HomeActivity, "Right!", Toast.LENGTH_SHORT).show();
//            }
//
//            override fun onAdapterAboutToEmpty(p0: Int) {
//                al.add("XML  is $i");
//                arrayAdapter?.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
//            }
//
//            override fun onScroll(p0: Float) {
//
//            }
//
//        })

    }


    //static function
    companion object {
        //if it returns only one thing
        fun newIntent(context: Context?) =  Intent(context, HomeActivity::class.java)
    }
}
