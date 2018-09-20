package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.fragment_number.view.*

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.CadastroActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.LogarActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.MainActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper.Permissons
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NumberFragment : Fragment() {

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

    private var mTextDDD: TextInputEditText? = null
    private var mTextNumeroCelular: TextInputEditText? = null
    private var mButtonPedirCodigo: Button? = null
    private var mJaPossuoCodigo: TextView? = null
    private var mTextJaCadastrado: TextView? = null
    private val mPermissions: Array<String> = arrayOf(
            Manifest.permission.SEND_SMS
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_number, container, false)

        Permissons.validarPermissao(mPermissions, activity!!, 1)

        mTextDDD = view.textDDD
        mTextNumeroCelular = view.textNumeroCelular
        mButtonPedirCodigo = view.buttonPedirCodigo
        mJaPossuoCodigo = view.jaPossuoCodigo
        mTextJaCadastrado = view.textJaCadastrado
        addMask()

        mButtonPedirCodigo!!.setOnClickListener { SMS() }
        mTextJaCadastrado!!.setOnClickListener { login() }

        return view
    }

    private fun login() {
        val intent = Intent(activity, LogarActivity::class.java)
        startActivity(intent)
        activity!!.finish()
    }

    private fun addMask() {
        val maskDDD = SimpleMaskFormatter("NN")
        val DDDWatcher = MaskTextWatcher(mTextDDD, maskDDD)
        mTextDDD!!.addTextChangedListener(DDDWatcher)

        val maskNumber = SimpleMaskFormatter("NNNNN NNNN")
        val numberWatcher = MaskTextWatcher(mTextNumeroCelular, maskNumber)
        mTextNumeroCelular!!.addTextChangedListener(numberWatcher)
    }

    private fun SMS() {
        if(mTextDDD!!.text.toString()!=""&&mTextNumeroCelular!!.text.toString()!="") {
            mButtonPedirCodigo!!.text = resources.getString(R.string.pedir_codigo)
            mButtonPedirCodigo!!.background = resources.getDrawable(R.drawable.botao_redondo_verde)

            val DDD = mTextDDD!!.text.toString()
            val number = mTextNumeroCelular!!.text.toString().replace(" ", "")

            sendSMS(DDD, number)
        } else {
            mButtonPedirCodigo!!.text = resources.getString(R.string.verificar_numero)
            mButtonPedirCodigo!!.background = resources.getDrawable(R.drawable.botao_redondo_vermelho)
        }
    }

    private fun sendSMS(ddd:String, number:String) : Boolean {
        try {
            val randomico = Random()
            mNumberRandomico = randomico.nextInt(9999-1000) + 1000
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage("+55$ddd$number", null, "Código de confirmação do PicPay: $mNumberRandomico", null, null)
            CadastroActivity.mPager!!.currentItem = 2
            return true
        } catch (e:Exception) {
            Log.d("erro", "Erro ao enviar sms ${e.message}")
            return false
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
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
        var mNumberRandomico = 0
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                NumberFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
