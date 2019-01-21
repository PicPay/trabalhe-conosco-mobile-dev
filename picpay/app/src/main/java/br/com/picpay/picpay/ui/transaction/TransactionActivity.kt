package br.com.picpay.picpay.ui.transaction

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.picpay.picpay.R
import br.com.picpay.picpay.base.BaseActivity

class TransactionActivity: BaseActivity<TransactionViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
    }

    override fun containerViewModel(): TransactionViewModel? {
        return ViewModelProviders
            .of(this)
            .get(TransactionViewModel::class.java)
    }
}