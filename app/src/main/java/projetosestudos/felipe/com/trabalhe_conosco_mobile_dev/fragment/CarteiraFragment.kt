package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_carteira.view.*

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.AddMoneyActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.AdicionarCartaoActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.AdapterMyAllCards
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserData
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserCardsViewModel
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserDataViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CarteiraFragment : Fragment() {
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

    private var mTextSaldo: TextView? = null
    private var mButtonAddMoney: Button? = null
    private var mAddCard: TextView? = null
    private var mUserDataViewModel: UserDataViewModel? = null
    private var mCardViewCards: CardView? = null
    private var mRecyclerMyAllCards: RecyclerView? = null
    private var mAdapterRecyclerMyAllCards: AdapterMyAllCards? = null
    private var mListCards: ArrayList<UserCards> = ArrayList()
    private var mUserCardsViewModel: UserCardsViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_carteira, container, false)

        mCardViewCards = view.cardViewCards
        mTextSaldo = view.textSaldo
        mButtonAddMoney = view.buttonAddMoney
        mAddCard = view.textAddCartao
        mRecyclerMyAllCards = view.recyclerMyAllCards

        mAdapterRecyclerMyAllCards = AdapterMyAllCards(activity!!, mListCards)

        mRecyclerMyAllCards!!.layoutManager = LinearLayoutManager(activity!!)
        mRecyclerMyAllCards!!.setHasFixedSize(true)
        mRecyclerMyAllCards!!.adapter = mAdapterRecyclerMyAllCards

        mUserCardsViewModel = ViewModelProviders.of(this).get(UserCardsViewModel::class.java)
        mUserCardsViewModel!!.getCards().observe(this, Observer<List<UserCards>> {
            if (!(it!!.isEmpty())) {
                mCardViewCards!!.visibility = View.GONE
                mRecyclerMyAllCards!!.visibility = View.VISIBLE
                it.forEach {
                    mListCards.add(it)
                }
                mAdapterRecyclerMyAllCards!!.notifyDataSetChanged()
            } else {
                mCardViewCards!!.visibility = View.VISIBLE
                mRecyclerMyAllCards!!.visibility = View.GONE
            }
        })

        mUserDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)
        mUserDataViewModel!!.getSaldo().observe(this, Observer<UserData> {
            if (it != null)
                mTextSaldo!!.text = "R$${it.saldo}"
        })

        mButtonAddMoney!!.setOnClickListener {
            if(!mListCards.isEmpty())
                activity!!.startActivityFromFragment(this@CarteiraFragment, Intent(activity, AddMoneyActivity::class.java), 0)
            else
                Toast.makeText(activity!!.applicationContext, "Você precisa adicionar um cartão primeiro", Toast.LENGTH_SHORT).show()
        }
        mAddCard!!.setOnClickListener {
            activity!!.startActivityFromFragment(this@CarteiraFragment, Intent(activity, AdicionarCartaoActivity::class.java), 0)
        }

        return view
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
                CarteiraFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
