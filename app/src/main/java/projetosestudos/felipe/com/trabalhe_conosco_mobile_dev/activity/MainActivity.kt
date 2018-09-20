package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_main.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.*

class MainActivity : AppCompatActivity(),
        HomeFragment.OnFragmentInteractionListener,
        TodosFragment.OnFragmentInteractionListener,
        MinhasFragment.OnFragmentInteractionListener,
        CarteiraFragment.OnFragmentInteractionListener,
        PagarFragment.OnFragmentInteractionListener,
        NotificationsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener {

    private var mBottomNavigationView: BottomNavigationViewEx? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager
        val fragmentTransaction = fragment.beginTransaction()
        fragmentTransaction.replace(R.id.frame, HomeFragment()).commit()

        configureNavigation()
    }

    private fun configureNavigation() {
        mBottomNavigationView = bottomNavigation
        mBottomNavigationView!!.enableAnimation(false)
        mBottomNavigationView!!.enableItemShiftingMode(false)
        mBottomNavigationView!!.enableShiftingMode(false)
        mBottomNavigationView!!.setTextVisibility(true)

        navigation(mBottomNavigationView!!)
    }

    private fun navigation(viewEx: BottomNavigationViewEx) {
        viewEx.onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

            val fragment = supportFragmentManager
            val fragmentTransaction = fragment.beginTransaction()

            when(item.itemId) {
                R.id.home -> {
                    fragmentTransaction.replace(R.id.frame, HomeFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.carteira -> {
                    fragmentTransaction.replace(R.id.frame, CarteiraFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.pagar -> {
                    fragmentTransaction.replace(R.id.frame, PagarFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.notificacoes -> {
                    fragmentTransaction.replace(R.id.frame, NotificationsFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ajustes -> {
                    fragmentTransaction.replace(R.id.frame, SettingsFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }
            }

            return@OnNavigationItemSelectedListener false
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
