package fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import apps.android.fattahnexx103.apptract.R


class SwipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    //    private var al = ArrayList<String>()
//    private var arrayAdapter: ArrayAdapter<String>? = null
//    private var i = 0


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
