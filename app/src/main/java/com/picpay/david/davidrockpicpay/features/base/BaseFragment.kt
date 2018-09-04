package com.picpay.david.davidrockpicpay.features.base

import android.app.DialogFragment
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picpay.david.davidrockpicpay.util.UiUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseFragment: DialogFragment() {

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(getLayout(), container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showMessage(message: String?) {
        if (message != null){
            UiUtil.Messages.message(context, message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showError(message: String?) {
        if (message != null){
            UiUtil.Messages.message(context, message)
        }
    }

    fun hideLoading() {
        getProgressDialog()?.cancel()
        getProgressDialog()?.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showLoading(@StringRes message: Int){
        getProgressDialog()?.cancel()
        getProgressDialog()?.dismiss()
        setProgressDialog(UiUtil.Dialogs.progress(context, message))
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onStop() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onMessage(message: String){
        //metodo necessario pois nem toda classe filho utiliza o eventbus
    }

    protected fun getProgressDialog(): ProgressDialog? {
        val activityInstance = activity
        if (activityInstance is BaseActivity){
            return activityInstance.loadingDialog
        }
        return null
    }

    protected fun setProgressDialog(dialog: ProgressDialog): ProgressDialog {
        val activityInstance = activity
        if (activityInstance is BaseActivity){
            activityInstance.loadingDialog = dialog
        }
        return dialog
    }

}