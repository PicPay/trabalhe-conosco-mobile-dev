package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.AdapterPager
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.adapter.AdapterUsers
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper.ApiHelper
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.User
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserData
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserDataViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), TodosFragment.OnFragmentInteractionListener, MinhasFragment.OnFragmentInteractionListener {

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

    private var mSaldoHome: TextView? = null
    private var mRecyclerUsers: RecyclerView? = null
    private var mAdapterUsers: AdapterUsers? = null
    private var mListUsers: ArrayList<User> = ArrayList()
    private var mViewPager: ViewPager? = null
    private var mTabLayout: TabLayout? = null
    private var mUserDataViewModel: UserDataViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        mSaldoHome = view.saldoHome
        mRecyclerUsers = view.recyclerUsuarios
        mViewPager = view.viewPager
        mTabLayout = view.tabs

        mAdapterUsers = AdapterUsers(activity!!, mListUsers)

        mUserDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)
        mUserDataViewModel!!.getSaldo().observe(this, Observer<UserData> {
            if(it != null) {
                mSaldoHome!!.text = "R$${it.saldo}"
            }

        })

        val adapterPager = AdapterPager(activity!!.supportFragmentManager, 2)

        mViewPager!!.adapter = adapterPager
        mViewPager!!.currentItem = 0
        mViewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                val tab = mTabLayout!!.getTabAt(position)
                tab!!.select()
            }

        })

        mTabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager!!.currentItem = tab.position
            }

        })

        val manager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerUsers!!.layoutManager = manager
        mRecyclerUsers!!.setHasFixedSize(true)
        mRecyclerUsers!!.adapter = mAdapterUsers

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
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
