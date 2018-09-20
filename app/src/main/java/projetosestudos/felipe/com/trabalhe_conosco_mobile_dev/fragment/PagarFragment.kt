package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_pagar.view.*

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.AdapterPagarUsuarios
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper.ApiHelper
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PagarFragment : Fragment() {

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

    private var mRecyclerPagarUsuarios: RecyclerView? = null
    private var mAdapterUsers: AdapterPagarUsuarios? = null
    private var mListUsers: ArrayList<User> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pagar, container, false)

        mRecyclerPagarUsuarios = view.recyclerPagarUsuarios

        mAdapterUsers = AdapterPagarUsuarios(activity!!, mListUsers)

        mRecyclerPagarUsuarios!!.layoutManager = LinearLayoutManager(activity!!)
        mRecyclerPagarUsuarios!!.setHasFixedSize(true)
        mRecyclerPagarUsuarios!!.adapter = mAdapterUsers

        return view
    }

    override fun onStart() {
        super.onStart()
        getUsuarios()
    }

    private fun getUsuarios() {
        mListUsers.clear()
        val call : Call<List<User>> = ApiHelper.RetrofitHelper.create().getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("data", "Error: ${t.message}")
                t.printStackTrace()
                Toast.makeText(activity!!.applicationContext, "Erro ao carregar usuários", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    for (i in response.body()!!.iterator()) {
                        mListUsers.add(i)
                    }

                    mAdapterUsers!!.notifyDataSetChanged()
                }
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
                PagarFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
