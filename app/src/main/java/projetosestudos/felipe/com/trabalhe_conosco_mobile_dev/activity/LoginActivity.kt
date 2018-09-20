package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_text.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R.string.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.SliderAdapter
import java.util.*

class LoginActivity : AppCompatActivity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mViewPager: ViewPager? = null
        var mListColor: ArrayList<Int>? = null
    }
    private var mIndicator: TabLayout? = null
    private var mListColorName: ArrayList<String>? = null
    private var mButtonCriarConta: Button? = null
    private var mJaCadastrado: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mViewPager = viewPager
        mIndicator = indicator
        mButtonCriarConta = buttonCriarConta
        mJaCadastrado = textJaCadastrado

        mListColor = ArrayList()
        mListColor!!.add(Color.WHITE)
        mListColor!!.add(Color.WHITE)
        mListColor!!.add(Color.WHITE)

        mListColorName = ArrayList()
        mListColorName!!.add(resources.getString(envie_receba))
        mListColorName!!.add(resources.getString(faca_recargas))
        mListColorName!!.add(resources.getString(compre_creditos))

        mViewPager!!.adapter = SliderAdapter(this, mListColor!!, mListColorName!!)
        mIndicator!!.setupWithViewPager(mViewPager, true)

        val timer = Timer()
        timer.schedule(SliderTimer(), 1000, 4000)

        mButtonCriarConta!!.setOnClickListener { criarConta() }
        mJaCadastrado!!.setOnClickListener { entrar() }
    }

    private fun criarConta() {
        val intent = Intent(this@LoginActivity, CadastroActivity::class.java)
        startActivity(intent)
    }

    private fun entrar() {
        val intent = Intent(this@LoginActivity, LogarActivity::class.java)
        startActivity(intent)
    }

    private class SliderTimer : TimerTask() {
        val handler: Handler = Handler()
        val pager = LoginActivity.mViewPager
        val list = LoginActivity.mListColor!!.size
        override fun run() {
            handler.postDelayed(Runnable {
                if(pager!!.currentItem < list - 1) {
                    pager.currentItem = pager.currentItem + 1
                } else {
                    pager.currentItem = 0
                }
            }, 4000)
        }

    }
}
