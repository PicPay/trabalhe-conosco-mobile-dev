package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R

class SliderAdapter : PagerAdapter {

    private var mContext: Context? = null
    private var mColor: ArrayList<Int>? = null
    private var mColorName: ArrayList<String>? = null

    constructor(context: Context, color: ArrayList<Int>, colorName: ArrayList<String>) {
        mContext = context
        mColor = color
        mColorName = colorName
    }

    override fun getCount(): Int {
        return mColor!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mInflater: LayoutInflater = mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView: View = mInflater.inflate(R.layout.item_slider, null)

        val mTextView: TextView = mView.findViewById(R.id.textView)
        val mLinearLayout: LinearLayout = mView.findViewById(R.id.linearLayout)

        mTextView.text = mColorName!![position]
        val mViewPager: ViewPager = container as ViewPager
        mViewPager.addView(mView, 0)

        return mView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val mViewPager: ViewPager = container as ViewPager
        val mView: View = `object` as View
        mViewPager.removeView(mView)
    }
}