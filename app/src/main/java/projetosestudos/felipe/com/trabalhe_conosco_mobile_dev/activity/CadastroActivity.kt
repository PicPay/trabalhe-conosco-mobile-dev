package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R.drawable.ic_voltar
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.PagerAdapter
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.ConfirmFragment
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.NameFragment
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment.NumberFragment

class CadastroActivity : AppCompatActivity(),
        NameFragment.OnFragmentInteractionListener,
        NumberFragment.OnFragmentInteractionListener,
        ConfirmFragment.OnFragmentInteractionListener {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mPager: ViewPager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val toolbar: Toolbar = toolbarMain
        toolbar.title = ""
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(ic_voltar)

        mPager = pager
        val pagerAdapter = PagerAdapter(supportFragmentManager, 3)
        mPager!!.setOnTouchListener { v, event -> return@setOnTouchListener true }
        mPager!!.adapter = pagerAdapter
        mPager!!.currentItem = 0

    }

    override fun onSupportNavigateUp(): Boolean {
        if(mPager!!.currentItem == 1) {
            mPager!!.currentItem = 0
        } else {
            finish()
        }
        return false
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
