package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import apps.android.fattahnexx103.apptract.R
import apps.android.fattahnexx103.apptract.activities.UserInfoActivity
import com.bumptech.glide.Glide
import util.UserData

class CardsAdapter(context: Context?, resourceId: Int, users: List<UserData>) : ArrayAdapter<UserData>(context, resourceId, users)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var user = getItem(position)
        var finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false) //either convertview if it exits or layout inflater

        var name = finalView.findViewById<TextView>(R.id.card_name_textView)
        var image = finalView.findViewById<ImageView>(R.id.card_imageView)
        var userInfo = finalView.findViewById<LinearLayout>(R.id.userInfoLayout)

        name.text = "${user.name}, ${user.age}"
        Glide.with(context)
            .load(user.imageUrl)
            .into(image)

        userInfo.setOnClickListener{
            finalView.context.startActivity(UserInfoActivity.newIntent(finalView.context, user.uid))
        }

        return finalView

    }

}