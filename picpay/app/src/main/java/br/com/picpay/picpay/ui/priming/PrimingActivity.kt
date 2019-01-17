package br.com.picpay.picpay.ui.priming

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.picpay.picpay.base.BaseActivity
import br.com.picpay.picpay.R
import kotlinx.android.synthetic.main.activity_priming_creditcard.*

class PrimingActivity: BaseActivity<PrimingViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_priming_creditcard)

        listenButton()
    }

    private fun listenButton() {
        priming_creditcard_button.setOnClickListener {
            viewModel?.setActivityRegister(this)
        }
    }

    override fun containerViewModel(): PrimingViewModel? {
        return ViewModelProviders
            .of(this)
            .get(PrimingViewModel::class.java)
    }
}