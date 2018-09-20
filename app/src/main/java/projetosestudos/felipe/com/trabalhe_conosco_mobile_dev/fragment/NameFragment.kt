package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.CadastroActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.LogarActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NameFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var mTextQualSeuNome: TextView? = null
    private var mTextNome: TextInputEditText? = null
    private var mTextSobrenome: TextInputEditText? = null
    private var mButtonAvancar: Button? = null
    private var mTextJaCadastrado: TextView? = null
    private var mNames: String? = null
    private var mDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mView: View =  inflater.inflate(R.layout.fragment_name, container, false)

        mTextQualSeuNome = mView.findViewById(R.id.textQualSeuNome)
        mTextNome = mView.findViewById(R.id.textNome)
        mTextSobrenome = mView.findViewById(R.id.textSobrenome)
        mButtonAvancar = mView.findViewById(R.id.buttonAvancar)
        mTextJaCadastrado = mView.findViewById(R.id.textJaCadastrado)
        mTextJaCadastrado!!.setOnClickListener { logar() }

        mNames = "Oi, "
        mTextNome!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!!.toString() == "") {
                    mTextQualSeuNome!!.text = resources.getString(R.string.qual_seu_nome)
                }
                mNames = "Oi, $s"
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mTextQualSeuNome!!.text = "Oi, $s"
            }

        })

        mTextSobrenome!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!!.toString() == "") {
                    mTextQualSeuNome!!.text = mNames
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mTextQualSeuNome!!.text = "$mNames $s"
            }

        })

        mButtonAvancar!!.setOnClickListener { verificarNomes() }

        return mView
    }

    private fun logar() {
        val intent = Intent(activity, LogarActivity::class.java)
        startActivity(intent)
        activity!!.finish()
    }

    private fun verificarNomes() {
        val nome: String = mTextNome!!.text.toString()
        val sobrenome: String = mTextSobrenome!!.text.toString()
        if(nome != "") {
            mButtonAvancar!!.text = resources.getString(R.string.text_avancar)
            mButtonAvancar!!.background = resources.getDrawable(R.drawable.botao_redondo_verde)
            if(sobrenome != "") {
                mButtonAvancar!!.text = resources.getString(R.string.text_avancar)
                mButtonAvancar!!.background = resources.getDrawable(R.drawable.botao_redondo_verde)
                validarNome()
                val handler = Handler()
                handler.postDelayed(Runnable {
                    mDialog!!.dismiss()
                    CadastroActivity.mPager!!.currentItem = 1
                }, 3000)
            } else {
                mButtonAvancar!!.text = resources.getString(R.string.digite_sobrenome)
                mButtonAvancar!!.background = resources.getDrawable(R.drawable.botao_redondo_vermelho)
            }
        } else {
            mButtonAvancar!!.text = resources.getString(R.string.digite_nome)
            mButtonAvancar!!.background = resources.getDrawable(R.drawable.botao_redondo_vermelho)
        }
    }

    private fun validarNome() {
        mDialog = ProgressDialog(activity)
        mDialog!!.isIndeterminate = true
        mDialog!!.setCancelable(false)
        mDialog!!.setMessage("validando nome")
        mDialog!!.create()
        mDialog!!.show()
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener") as Throwable
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                NameFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
