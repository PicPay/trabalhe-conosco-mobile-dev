package com.jvtnascimento.picpay.ui.views

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.jvtnascimento.picpay.R
import com.jvtnascimento.picpay.adapters.UserAdapter
import com.jvtnascimento.picpay.application.BaseApplication
import com.jvtnascimento.picpay.models.CreditCard
import com.jvtnascimento.picpay.models.TransactionResponse
import com.jvtnascimento.picpay.models.User
import com.jvtnascimento.picpay.presenter.MainPresenter
import com.jvtnascimento.picpay.ui.components.BottomSheetDialog
import com.jvtnascimento.picpay.ui.contracts.MainViewContractInterface
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainViewContractInterface {

    @BindView(R.id.userList) lateinit var userList: RecyclerView
    @BindView(R.id.searchView) lateinit var searchView: SearchView
    @BindView(R.id.progressBar) lateinit var progressBar: ProgressBar

    val PAYMENT_ACTIVITY: Int = 0

    @Inject lateinit var presenter: MainPresenter
    private var users: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        this.configureComponents()
        this.loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PAYMENT_ACTIVITY) {
                if (data != null) {
                    val transaction = data.getSerializableExtra("transaction") as TransactionResponse
                    val creditCard = data.getSerializableExtra("creditCard") as CreditCard
                    this.showReceipt(transaction, creditCard)
                }
            }
        }
    }

    override fun showError(error: Throwable) {
        AlertDialog.Builder(this)
            .setTitle("Oops...")
            .setMessage("Não foi possível obter a lista de contatos =(")
            .setCancelable(false)
            .setPositiveButton("Tentar Novamente"){ _, _ ->
                this.loadData()
            }
            .show()
    }

    override fun showProgressBar() {
        this.userList.visibility = View.GONE
        this.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        this.userList.visibility = View.VISIBLE
        this.progressBar.visibility = View.GONE
    }

    override fun showUsers(users: ArrayList<User>) {
        this.users = ArrayList(users.sortedBy { user -> user.username })
        this.configureView()
    }

    private fun loadData() {
        this.presenter.getUsers()
    }

    private fun configureComponents() {
        (application as BaseApplication).component.inject(this)
        this.presenter.attach(this)
    }

    private fun configureView() {
        this.userList.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(this.users, this)
        this.userList.adapter = adapter

        adapter.onClick = { user ->
            val intent = Intent( this, PaymentActivity::class.java)
            intent.putExtra("user", user)
            startActivityForResult(intent, PAYMENT_ACTIVITY)
        }

        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        this.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        this.searchView.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })
    }

    private fun showReceipt(transactionResponse: TransactionResponse, creditCard: CreditCard) {
        val bottomSheetDialog = BottomSheetDialog()
        bottomSheetDialog.setUpModels(transactionResponse, creditCard)
        bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
    }

}
