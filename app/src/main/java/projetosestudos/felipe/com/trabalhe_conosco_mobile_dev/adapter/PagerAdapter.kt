package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.ConfirmFragment
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.NameFragment
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.NumberFragment

class PagerAdapter : FragmentStatePagerAdapter {

    private var mNumberTabs: Int? = null

    constructor(fragmentManager: FragmentManager, numberTabs: Int) : super(fragmentManager) {
        mNumberTabs = numberTabs
    }

    override fun getItem(position: Int): Fragment? {
        when(position) {
            0 -> {
                val nameFragment = NameFragment()
                return nameFragment
            }
            1 -> {
                val numberFragment = NumberFragment()
                return numberFragment
            }
            2 -> {
                val confirmFragment = ConfirmFragment()
                return confirmFragment
            }
            else -> {
                return null
            }
        }
    }

    override fun getCount(): Int {
        return mNumberTabs!!
    }
}