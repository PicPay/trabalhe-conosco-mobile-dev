package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_logar.*
import kotlinx.android.synthetic.main.toolbarverde.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R.drawable.ic_voltar_branco
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R.string.*

class LogarActivity : AppCompatActivity() {

    private var mEditDigiteQualquerCoisaUm: TextInputEditText? = null
    private var mEditDigiteQualquerCoisaDois: TextInputEditText? = null
    private var mButtonEntrar: Button? = null
    private var mTextEsqueciSenha: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logar)

        val toolbar = toolbarMainVerde
        toolbar.title = resources.getString(login)
        toolbar.setTitleTextColor(resources.getColor(R.color.colorAccent))
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(ic_voltar_branco)

        mEditDigiteQualquerCoisaUm = digiteQualquerCoisaUm
        mEditDigiteQualquerCoisaDois = digiteQualquerCoisaDois
        mButtonEntrar = buttonEntrar
        mTextEsqueciSenha = textEsqueciSenha

        mButtonEntrar!!.setOnClickListener{ mainActivity() }
        mTextEsqueciSenha!!.setOnClickListener { Toast.makeText(applicationContext, "Esqueci a senha", Toast.LENGTH_SHORT).show() }
    }

    private fun mainActivity() {
        if(mEditDigiteQualquerCoisaUm!!.text.toString()!=""&&mEditDigiteQualquerCoisaDois!!.text.toString()!="") {
            mButtonEntrar!!.text = resources.getString(entrar)
            val intent = Intent(this@LogarActivity, MainActivity::class.java)
            startActivity(intent)
            this@LogarActivity.finish()
        } else {
            mButtonEntrar!!.text = resources.getString(campos_vazios)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}
