package com.v1pi.picpay_teste.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.v1pi.picpay_teste.R
import kotlinx.android.synthetic.main.with_credit_card_fragment.*

class WithCreditCardFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.with_credit_card_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments

        bundle?.let {
            bindDataCreditToView(it.getString("number"))
        }
    }

    private fun bindDataCreditToView(final : String) {
        txt_credit_card.text = getString(R.string.credit_card_text, final.substring(final.length - 4, final.length))
    }
}