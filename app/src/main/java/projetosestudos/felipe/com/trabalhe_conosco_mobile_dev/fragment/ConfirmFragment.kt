package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_confirm.view.*

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.MainActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConfirmFragment : Fragment() {

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

    private var mEditNumerConfirm: TextInputEditText? = null
    private var mButtonConfirmar: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_confirm, container, false)

        mEditNumerConfirm = view.textNumeroConfirmar
        mButtonConfirmar = view.buttonConfirmar
        mButtonConfirmar!!.setOnClickListener { verifyCode() }

        return view
    }

    private fun verifyCode() {
        val number = mEditNumerConfirm!!.text.toString()
        if(number == NumberFragment.mNumberRandomico.toString()) {
            activity!!.startActivity(Intent(activity!!.applicationContext, MainActivity::class.java))
            activity!!.finish()
            Toast.makeText(activity!!.applicationContext, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(activity!!.applicationContext, "Código de confirmação incorreto", Toast.LENGTH_SHORT).show()
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
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ConfirmFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
