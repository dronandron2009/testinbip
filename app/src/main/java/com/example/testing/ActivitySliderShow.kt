package com.example.testing

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.testing.Utils.Companion.CERTIFICATE_REGISTRATION
import com.example.testing.Utils.Companion.CHECK
import com.example.testing.Utils.Companion.DELIVERS_LICENSE
import com.example.testing.Utils.Companion.REGISTRATION_NUMBER

class ActivitySliderShow: AppCompatActivity() {

    var mSLideViewPager: ViewPager? = null
    var mDotLayout: LinearLayout? = null
    var llSlaiderShow: LinearLayout? = null
    var tvFinishSlider: TextView? = null
    var viewPagerAdapter: ViewPagerAdapter? = null
    var nextbtn:ImageView? = null
    private var valueRegistrationNumber: String? = null
    private var valueCertificateRegistration: String? = null
    private var valueDeliversLicense: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_show)

        valueRegistrationNumber = intent.getStringExtra(REGISTRATION_NUMBER)
        valueCertificateRegistration = intent.getStringExtra(CERTIFICATE_REGISTRATION)
        valueDeliversLicense = intent.getStringExtra(DELIVERS_LICENSE)

        nextbtn = findViewById<View>(R.id.iv_nextbtn) as ImageView
        mSLideViewPager = findViewById<View>(R.id.slideViewPager) as ViewPager
        mDotLayout = findViewById<View>(R.id.indicator_layout) as LinearLayout
        llSlaiderShow = findViewById<View>(R.id.ll_slider_show) as LinearLayout
        tvFinishSlider = findViewById<View>(R.id.tv_finish_slider) as TextView
        nextbtn?.setOnClickListener(View.OnClickListener {
            if (getitem(0) < 3) mSLideViewPager!!.setCurrentItem(getitem(1), true) else {
                tvFinishSlider?.visibility = View.VISIBLE
                mDotLayout?.visibility = View.GONE
                nextbtn?.visibility = View.GONE
                llSlaiderShow?.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_ff8))
            }
        })

        tvFinishSlider?.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.putExtra(REGISTRATION_NUMBER, valueRegistrationNumber)
            i.putExtra(CERTIFICATE_REGISTRATION, valueCertificateRegistration)
            i.putExtra(DELIVERS_LICENSE, valueDeliversLicense)
            i.putExtra(CHECK, true)
            startActivity(i)
            finish()
        }


        viewPagerAdapter = ViewPagerAdapter(this)
        mSLideViewPager!!.adapter = viewPagerAdapter
        setUpindicator(0)
        mSLideViewPager!!.addOnPageChangeListener(viewListener)
    }

    fun setUpindicator(position: Int) {
        val indiactorArray = arrayOfNulls<TextView>(4)
        mDotLayout!!.removeAllViews()
        var index = 0

        while (index < indiactorArray.size) {
            indiactorArray[index] = TextView(this)
            indiactorArray[index]?.text = Html.fromHtml("&#8226")
            indiactorArray[index]?.textSize = 35f
            indiactorArray[index]?.setTextColor(ContextCompat.getColor(this, R.color.blue_82))
            mDotLayout?.addView(indiactorArray[index])
            index++
        }
        if (position == indiactorArray.size - 1){
            tvFinishSlider?.visibility = View.VISIBLE
            mDotLayout?.visibility = View.GONE
            nextbtn?.visibility = View.GONE
            llSlaiderShow?.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_ff8))
        }else{
            tvFinishSlider?.visibility = View.GONE
            mDotLayout?.visibility = View.VISIBLE
            nextbtn?.visibility = View.VISIBLE
            llSlaiderShow?.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_639))
        }

        indiactorArray[position]?.setTextColor(ContextCompat.getColor(this, R.color.white))


    }

    var viewListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            setUpindicator(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    private fun getitem(i: Int): Int {
        return mSLideViewPager!!.currentItem + i
    }


}