package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.MinhasFragment
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.TodosFragment

class AdapterPager : FragmentStatePagerAdapter {

    private var mNumItens: Int? = null

    constructor(fragmentManager: FragmentManager, numItens: Int) : super(fragmentManager) {
        mNumItens = numItens
    }

    override fun getItem(position: Int): Fragment? {
        when(position) {
            0 -> {
                val todosFragment = TodosFragment()
                return todosFragment
            }
            1 -> {
                val minhasFragment = MinhasFragment()
                return minhasFragment
            }
            else -> {
                return null
            }
        }
    }

    override fun getCount(): Int {
        return mNumItens!!
    }

}