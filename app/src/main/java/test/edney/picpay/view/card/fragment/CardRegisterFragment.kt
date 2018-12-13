package test.edney.picpay.view.card.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import test.edney.picpay.database.CardEntity
import test.edney.picpay.databinding.FragmentCardRegisterBinding
import test.edney.picpay.view.payment.PaymentActivity
import test.edney.picpay.viewmodel.CardRegisterVM

class CardRegisterFragment : Fragment() {

    private lateinit var binding: FragmentCardRegisterBinding
    private lateinit var viewmodel: CardRegisterVM
    private var userJson: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCardRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        loadArgs()
        viewmodel()
        ui()
    }

    private fun viewmodel(){

        viewmodel = ViewModelProviders.of(this).get(CardRegisterVM::class.java)

        viewmodel.card.observe(this, Observer {
            if(it != null){
                binding.tvNumber.setText(it.number)
                binding.tvName.setText(it.name)
                binding.tvExpiration.setText(it.expiration)
                binding.tvCvv.setText(it.cvv)
            }
        })
    }

    private fun ui(){
        binding.ui = object : CardRegisterUI{
            override fun actionBack() {
                activity?.finish()
            }
            override fun actionSave() {
                val card = CardEntity()
                val intent = Intent(activity, PaymentActivity::class.java)

                card.number = binding.tvNumber.text.toString()
                card.name = binding.tvName.text.toString()
                card.expiration = binding.tvExpiration.text.toString()
                card.cvv = binding.tvCvv.text.toString()
                viewmodel.addCard(card)

                intent.putExtra("user", userJson)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun loadArgs(){
        if(arguments != null)
            userJson = arguments?.getString("user")
    }
}
