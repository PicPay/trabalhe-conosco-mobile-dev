package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_minhas.view.*

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.AdapterTransacoes
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.AllTransactionsViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MinhasFragment : Fragment() {
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

    private var mRecyclerMinhas: RecyclerView? = null
    private var mAdapterTransacoes: AdapterTransacoes? = null
    private var mListTransactions: ArrayList<AllTransactions> = ArrayList()
    private var mAllTransactionsViewModel: AllTransactionsViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_minhas, container, false)

        mRecyclerMinhas = view.recyclerMinhas

        ViewCompat.setNestedScrollingEnabled(mRecyclerMinhas!!, false)

        mAdapterTransacoes = AdapterTransacoes(activity!!, mListTransactions)

        val linearLayoutManager = LinearLayoutManager(activity!!)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        mRecyclerMinhas!!.layoutManager = linearLayoutManager
        mRecyclerMinhas!!.setHasFixedSize(true)
        mRecyclerMinhas!!.adapter = mAdapterTransacoes

        return view
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mListTransactions.clear()
        mAllTransactionsViewModel = ViewModelProviders.of(this).get(AllTransactionsViewModel::class.java)
        mAllTransactionsViewModel!!.getTransactions().observe(this, Observer<List<AllTransactions>> {
            if (!(it!!.isEmpty())) {
                it.forEach {
                    if(!mListTransactions.contains(it)) {
                        mListTransactions.add(it)
                    }
                }
                mAdapterTransacoes!!.notifyDataSetChanged()
            }
        })
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
                MinhasFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
