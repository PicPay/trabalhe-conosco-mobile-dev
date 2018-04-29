package com.v1pi.picpay_teste.Fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.v1pi.picpay_teste.R

class WithoutCreditCardFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.without_credit_card_fragment, container, false)
    }
}