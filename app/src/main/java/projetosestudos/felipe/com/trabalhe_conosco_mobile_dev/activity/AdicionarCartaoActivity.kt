package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.activity_adicionar_cartao.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserCardsViewModel

class AdicionarCartaoActivity : AppCompatActivity() {

    private var mTextCardNumber: TextView? = null
    private var mTextNomeCartao: TextView? = null
    private var mTextValidateCard: TextView? = null
    private var mInputNumber: TextInputLayout? = null
    private var mInputNome: TextInputLayout? = null
    private var mInputValidade: TextInputLayout? = null
    private var mEditCardNumber: TextInputEditText? = null
    private var mEditCardName: TextInputEditText? = null
    private var mEditValidadeNumber: TextInputEditText? = null
    private var mButtonAfter: Button? = null
    private var mButtonBefore: Button? = null
    private var mUserCardsViewModel: UserCardsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_cartao)

        mTextCardNumber = textCardNumber
        mTextNomeCartao = textNomeCartao
        mTextValidateCard = textValidateCard
        mInputNumber = layoutNumber
        mInputNome = layoutNome
        mInputValidade = layoutValidade
        mEditCardNumber = inputNumeroCartao
        mEditCardName = inputNomeCartao
        mEditValidadeNumber = inputValidadeCartao
        mButtonAfter = buttonAfter
        mButtonBefore = buttonBefore

        addMaskCardNumber()
        addMaskCardName()
        addMaskValidateNumber()

        mButtonAfter!!.setOnClickListener { verificarCampos() }
        mButtonBefore!!.setOnClickListener { verificarCamposVoltar() }

        mUserCardsViewModel = ViewModelProviders.of(this).get(UserCardsViewModel::class.java)

    }

    private fun verificarCampos() {
        if(mInputNumber!!.visibility == View.VISIBLE) {

            mInputNumber!!.visibility = View.GONE
            mInputNome!!.visibility = View.VISIBLE
            mInputValidade!!.visibility = View.GONE
            mButtonBefore!!.visibility = View.VISIBLE

            mEditCardNumber!!.requestFocus()

        } else if(mInputNome!!.visibility == View.VISIBLE) {

            mInputNumber!!.visibility = View.GONE
            mInputNome!!.visibility = View.GONE
            mInputValidade!!.visibility = View.VISIBLE
            mButtonBefore!!.visibility = View.VISIBLE

             mEditCardName!!.requestFocus()

        } else if (mInputValidade!!.visibility == View.VISIBLE) {

            val cardNumber = mEditCardNumber!!.text.toString()
            val cardName = mEditCardName!!.text.toString()
            val cardValidate = mEditValidadeNumber!!.text.toString()
            if(cardValidate!="") {
                val card = UserCards(cardNumber, cardName, cardValidate)
                mUserCardsViewModel!!.insert(card)
                this@AdicionarCartaoActivity.finish()
            } else
                Toast.makeText(applicationContext, "Adicione a data de validade", Toast.LENGTH_SHORT).show()

        }
    }

    private fun verificarCamposVoltar() {
        if(mInputValidade!!.visibility == View.VISIBLE) {
            mInputValidade!!.visibility = View.GONE
            mInputNome!!.visibility = View.VISIBLE
            mInputNumber!!.visibility = View.GONE
        } else if(mInputNome!!.visibility == View.VISIBLE) {
            mInputValidade!!.visibility = View.GONE
            mInputNome!!.visibility = View.GONE
            mInputNumber!!.visibility = View.VISIBLE
            mButtonBefore!!.visibility = View.GONE
        }
    }

    private fun addMaskCardNumber() {
        val mask = SimpleMaskFormatter("NNNN NNNN NNNN NNNN")
        val watcher = MaskTextWatcher(mEditCardNumber, mask)
        mEditCardNumber!!.addTextChangedListener(watcher)

        mEditCardNumber!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!!.length == 0)
                    mTextCardNumber!!.text = resources.getString(R.string.cardnumber)
                else {
                    mTextCardNumber!!.textSize = 13f
                    if(s.length<=19) {
                        mTextCardNumber!!.text = s.toString()
                    }
                }
                if(s.length>=17&&s.length<=20)
                    mButtonAfter!!.isEnabled = true
                else
                    mButtonAfter!!.isEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private fun addMaskCardName() {
        mEditCardName!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!!.length>0) {
                    mButtonAfter!!.isEnabled = true
                    if (s.length <= 15)
                        mTextNomeCartao!!.text = s.toString().toUpperCase()
                } else {
                    mButtonAfter!!.isEnabled = false
                    mTextNomeCartao!!.text = resources.getString(R.string.nomecartao)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private fun addMaskValidateNumber() {
        val mask = SimpleMaskFormatter("NN/NN")
        val watcher = MaskTextWatcher(mEditValidadeNumber, mask)
        mEditValidadeNumber!!.addTextChangedListener(watcher)

        mEditValidadeNumber!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!!.length>0) {
                    if(s.length<=5) {
                        mTextValidateCard!!.text = s.toString()
                        if(s.length==5)
                            mButtonAfter!!.isEnabled = true
                    }
                } else {
                    mButtonAfter!!.isEnabled = false
                    mTextValidateCard!!.text = resources.getString(R.string.cvvnumber)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Cancelar inserção do cartão")
        dialog.setMessage("Deseja realmente cancelar a inserção do cartão?")
        dialog.setCancelable(false)
        dialog.setPositiveButton("Confirmar") {
            dialog, which ->
            this@AdicionarCartaoActivity.finish()
        }
        dialog.setNegativeButton("Cancelar", null)
        dialog.create()
        dialog.show()
    }
}
