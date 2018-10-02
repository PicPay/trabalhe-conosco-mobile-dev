package com.example.igor.picpayandroidx.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.igor.picpayandroidx.R
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_success.*
import android.content.Intent
import android.view.MenuItem


class SuccessActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        tv_transaction_finished.text = getString(R.string.transacao_realizada_para)

        val transformation = RoundedTransformationBuilder()
                .cornerRadiusDp(75f)
                .oval(false)
                .build()

        Picasso.with(this)
                .load(intent.getStringExtra(resources.getString(R.string.success_intent_img_key)))
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .error(R.drawable.ic_person_outline_black_24dp)
                .fit()
                .transform(transformation)
                .into(iv_success)

        var value:String = intent.getFloatExtra(resources.getString(R.string.success_intent_value_key), 0f).toString()
                .replace(".", ",")

        tv_success_name_and_value.text = intent.getStringExtra(resources.getString(R.string.success_intent_name_key)) + "\n" +
                "No valor de: R$" + value


        btn_back_to_main_activity.setOnClickListener {

            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
