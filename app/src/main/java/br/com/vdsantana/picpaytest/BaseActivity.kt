package br.com.vdsantana.picpaytest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.vdsantana.picpaytest.di.component.AppComponent
import br.com.vdsantana.picpaytest.mvp.BasePresenter
import br.com.vdsantana.picpaytest.mvp.BaseView

/**
 * Created by vd_sa on 30/03/2018.
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {

    private var presenter: BasePresenter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityInject()
    }

    protected abstract fun onActivityInject()

    fun getAppcomponent(): AppComponent = AppApplication.appComponent

    override fun setPresenter(presenter: BasePresenter<*>) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        presenter = null
    }
}