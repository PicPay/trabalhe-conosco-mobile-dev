package br.com.picpay.picpay.ui.priming

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import br.com.picpay.picpay.base.BaseActivity
import br.com.picpay.picpay.R
import br.com.picpay.picpay.utils.Constants
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constants.SAVE_CARD -> {
                if(resultCode == Activity.RESULT_OK) {
                    viewModel?.finishActivity(this, true)
                } else viewModel?.finishActivity(this, false)
            }
        }
    }
}