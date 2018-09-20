package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_payment.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.R
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper.ApiHelper
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper.Notification
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.*
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.AllTransactionsViewModel
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserCardsViewModel
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel.UserDataViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {

    private var mUser: User? = null
    private var mImageViewUser: CircleImageView? = null
    private var mTextUsername: TextView? = null
    private var mEditValuePay: EditText? = null
    private var mButtonPay: Button? = null
    private var mUserDataViewModel: UserDataViewModel? = null
    private var mUserCardsViewModel: UserCardsViewModel? = null
    private var mAllTransactionsViewModel: AllTransactionsViewModel? = null
    private var mSaldo: Double = 0.0
    private var mCardValidate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val toolbar = toolbar
        toolbar.title = resources.getString(R.string.nova_transacao)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_voltar)

        mUserDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)
        mUserDataViewModel!!.getSaldo().observe(this, Observer<UserData> {
            if (it != null)
                mSaldo = it.saldo
        })

        mUserCardsViewModel = ViewModelProviders.of(this).get(UserCardsViewModel::class.java)
        mUserCardsViewModel!!.getCards().observe(this, Observer<List<UserCards>> {
            if (!(it!!.isEmpty())) {
                mCardValidate = it[0].card_validate
            }
        })

        mAllTransactionsViewModel = ViewModelProviders.of(this).get(AllTransactionsViewModel::class.java)

        mImageViewUser = imageViewUser
        mTextUsername = textUserName
        mEditValuePay = editValuePay
        mButtonPay = buttonPay

        val bundle = intent.extras
        mUser = bundle.get("user") as User

        Glide.with(this).load(mUser!!.img).into(mImageViewUser)
        mTextUsername!!.text = mUser!!.username

        mButtonPay!!.setOnClickListener {
            val value = mEditValuePay!!.text.toString()
            if(value!="") {
                verifySaldo(value)
            } else
                Toast.makeText(applicationContext, "Digite um valor para pagar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifySaldo(valor: String) {
        val valorDouble = valor.toDouble()
        if (valorDouble <= mSaldo && valorDouble!=0.0)
            payment(valorDouble)
        else
            Toast.makeText(applicationContext,"Valor digitado maior que saldo disponível em conta", Toast.LENGTH_SHORT).show()
    }

    private fun payment(valorPagar: Double) {
        val payment = Payment("1111111111111111", 111, valorPagar, mCardValidate!!, mUser!!.id!!)
        val call: Call<ResponsePayment> = ApiHelper.RetrofitHelper.create().postPayment(payment)
        call.enqueue(object : Callback<ResponsePayment> {
            override fun onFailure(call: Call<ResponsePayment>, t: Throwable) {
                Log.d("onFailure", "Error payment: ${t.message}")
                t.printStackTrace()
                Toast.makeText(applicationContext, "Não foi possível realizar o pagamento", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponsePayment>, response: Response<ResponsePayment>) {
                if (response.isSuccessful) {
                    if (response.body()!!.transaction.sucesso) {
                        val saldo = mSaldo - valorPagar
                        mUserDataViewModel!!.update(saldo)
                        val transaction = AllTransactions(mUser!!.username!!, mUser!!.img!!, valorPagar.toString())
                        mAllTransactionsViewModel!!.insert(transaction)
                        val notification = Notification(this@PaymentActivity, "Seu pagamento a ${mUser!!.username!!} é só sucesso :)")
                        notification.createNotification()
                        this@PaymentActivity.finish()
                    } else
                        Toast.makeText(applicationContext, "Seu pagamento foi recusado", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(applicationContext, "Verifique sua conexão com a internet", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
        this@PaymentActivity.finish()
        return false
    }
}
