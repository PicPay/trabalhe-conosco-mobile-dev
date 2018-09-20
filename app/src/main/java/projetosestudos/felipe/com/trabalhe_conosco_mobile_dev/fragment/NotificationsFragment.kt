package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity.MainActivity
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.AdapterNotifications
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.AllTransactionsViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotificationsFragment : Fragment() {

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

    private var mRecyclerNotifications: RecyclerView? = null
    private var mAdapterNotifications: AdapterNotifications? = null
    private var mListTransactions: ArrayList<AllTransactions> = ArrayList()
    private var mAllTransactionsViewModel: AllTransactionsViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        mRecyclerNotifications = view.recyclerNotifications
        mAdapterNotifications = AdapterNotifications(mListTransactions)

        mAllTransactionsViewModel = ViewModelProviders.of(this).get(AllTransactionsViewModel::class.java)

        val linearLayoutManager = LinearLayoutManager(activity!!)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        mRecyclerNotifications!!.layoutManager = linearLayoutManager
        mRecyclerNotifications!!.setHasFixedSize(true)
        mRecyclerNotifications!!.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
        mRecyclerNotifications!!.adapter = mAdapterNotifications

        return view
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mListTransactions.clear()
        mAllTransactionsViewModel!!.getTransactions().observe(this, Observer<List<AllTransactions>> {
            it!!.forEach {
                if (!mListTransactions.contains(it)) {
                    mListTransactions.add(it)
                }
            }
            mAdapterNotifications!!.notifyDataSetChanged()
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
                NotificationsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
