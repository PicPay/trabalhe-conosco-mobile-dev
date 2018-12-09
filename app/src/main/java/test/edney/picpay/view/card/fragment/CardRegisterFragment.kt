package test.edney.picpay.view.card.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import test.edney.picpay.database.CardEntity
import test.edney.picpay.databinding.FragmentCardRegisterBinding
import test.edney.picpay.view.payment.PaymentActivity
import test.edney.picpay.viewmodel.CardRegisterVM

class CardRegisterFragment : Fragment() {

    private lateinit var binding: FragmentCardRegisterBinding
    private lateinit var viewmodel: CardRegisterVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCardRegisterBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProviders.of(this).get(CardRegisterVM::class.java)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ui()
    }

    private fun ui(){
        binding.ui = object : CardRegisterUI{
            override fun actionSave() {
                val card = CardEntity()

                card.number = binding.tvNumber.text.toString()
                card.name = binding.tvName.text.toString()
                card.expiration = binding.tvExpiration.text.toString()
                card.cvv = binding.tvCvv.text.toString()
                viewmodel.addCard(card)

                startActivity(Intent(activity, PaymentActivity::class.java))
                activity?.finish()
            }
        }
    }
}
