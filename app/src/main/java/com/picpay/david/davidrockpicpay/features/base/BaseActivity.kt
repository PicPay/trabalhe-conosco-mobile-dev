package com.picpay.david.davidrockpicpay.features.base

import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.picpay.david.davidrockpicpay.util.UiUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity: AppCompatActivity() {

    var loadingDialog: ProgressDialog? = null

    companion object {
        var lastingLoading: ProgressDialog? = null
    }


    fun showMessage(message: String?) {
        if (message != null){
            UiUtil.Messages.message(this, message)
        }
    }

    fun showError(message: String?) {
        if (message != null){
            UiUtil.Messages.message(this, message)
        }
    }

    fun hideLoading() {
        loadingDialog?.cancel()
        loadingDialog?.dismiss()
    }

    fun showLoading(@StringRes message: Int){
        loadingDialog?.cancel()
        loadingDialog?.dismiss()
        loadingDialog = UiUtil.Dialogs.progress(this, message)
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        //InternalMessages(Actions.APP_FOREGROUND)
    }

    override fun onStop() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        //InternalMessages(Actions.APP_BACKGROUND)
        super.onStop()

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(message: String){
        Log.i("ACTION", message)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}