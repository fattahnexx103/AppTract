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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import apps.android.fattahnexx103.apptract.R
import com.google.android.material.tabs.TabLayout
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import fragments.MatchesFragment
import fragments.ProfileFragment
import fragments.SwipeFragment
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    //declare the fragments (initially we set them to null)
    private var profileFragment: ProfileFragment? = null
    private var swipeFragment: SwipeFragment? = null
    private var matchesFragment: MatchesFragment? = null

    //create 3 tabs (Initially we set them to null)
    private var profileTab: TabLayout.Tab? = null
    private var  swipeTab: TabLayout.Tab? = null
    private var matchesTab: TabLayout.Tab? = null


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
                        }
                        replaceFragment(profileFragment!!)
                    }
                    swipeTab ->{
                        if(swipeFragment == null){
                            swipeFragment = SwipeFragment()
                        }
                        replaceFragment(swipeFragment!!)

                    }
                    matchesTab ->{
                        if(matchesFragment == null){
                            matchesFragment = MatchesFragment()
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


    //static function
    companion object {
        //if it returns only one thing
        fun newIntent(context: Context?) =  Intent(context, HomeActivity::class.java)
    }
}
