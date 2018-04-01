package br.com.vdsantana.picpaytest.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import br.com.vdsantana.picpaytest.BaseActivity
import br.com.vdsantana.picpaytest.R
import br.com.vdsantana.picpaytest.main.di.DaggerMainActivityComponent
import br.com.vdsantana.picpaytest.main.di.MainActivityModule
import br.com.vdsantana.picpaytest.transaction.TransactionActivity
import br.com.vdsantana.picpaytest.users.User
import br.com.vdsantana.picpaytest.users.presenter.UserPresenter
import br.com.vdsantana.picpaytest.users.presenter.UserView
import br.com.vdsantana.picpaytest.utils.picasso.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import net.idik.lib.slimadapter.SlimAdapter
import net.idik.lib.slimadapter.viewinjector.IViewInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), UserView {

    @Inject
    lateinit var mUsersPresenter: UserPresenter

    private lateinit var mSlimAdapter: SlimAdapter

    private val EXTRA_USER = "extra_user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
    }

    private fun initializeUI() {
        usersList.layoutManager = LinearLayoutManager(this)
        mSlimAdapter = SlimAdapter.create()
                .register<User>(R.layout.item_user) { user, injector ->
                    injector.text(R.id.contactName, user.name)
                    injector.text(R.id.username, user.username)
                    injector.clicked(R.id.mainLayout, {
                        val intent = Intent(this, TransactionActivity::class.java)
                        intent.putExtra(EXTRA_USER, user)
                        startActivity(intent)
                    })
                    injector.with(R.id.avatar, IViewInjector.Action<ImageView>() { avatarImage: ImageView ->
                        Picasso.get()
                                .load(user.img)
                                .placeholder(R.drawable.ic_default_avatar)
                                .transform(CircleTransformation())
                                .into(avatarImage);
                    })
                }.attachTo(usersList)

        mUsersPresenter.getUsers()
    }

    override fun onError() {
        Toast.makeText(this, "Erro ao obter lista de usuários!", Toast.LENGTH_SHORT).show()
    }

    override fun onUsersRequestResponse(users: List<User>) {
        usersList.visibility = View.VISIBLE
        mSlimAdapter.updateData(users)
    }

    override fun showProgress() {
        progressUsers.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressUsers.visibility = View.GONE
    }

    override fun noResult() {
        Toast.makeText(this, "Nenhum usuário encontrado...", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityInject() {
        DaggerMainActivityComponent.builder().appComponent(getAppcomponent())
                .mainActivityModule(MainActivityModule())
                .build()
                .inject(this)

        mUsersPresenter.attachView(this)
    }
}
