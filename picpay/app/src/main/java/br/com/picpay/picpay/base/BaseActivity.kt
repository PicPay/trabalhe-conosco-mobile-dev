package br.com.picpay.picpay.base

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity <VM: ViewModel>: AppCompatActivity() {

    var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = if (viewModel == null) containerViewModel() else viewModel
    }

    abstract fun containerViewModel(): VM?
}