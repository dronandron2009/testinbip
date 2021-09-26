package com.example.testing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(context: Context) : PagerAdapter() {

    var context: Context? = context

    val images = intArrayOf(
        R.drawable.pay_in_app,
        R.drawable.push_in_app,
        R.drawable.chek_in_app,
        R.drawable.gai_in_app
    )

    val headings = intArrayOf(
        R.string.pay_in_app,
        R.string.push_in_app,
        R.string.check_in_app,
        R.string.gai_in_app
    )



    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slayder_layout, container, false)
        val slidetitleimage = view.findViewById<View>(R.id.titleImage) as ImageView
        val slideHeading = view.findViewById<View>(R.id.texttitle) as TextView

        slideHeading.setText(headings[position])
        slidetitleimage.setImageResource(images[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
