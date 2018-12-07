package test.edney.picpay.view.card.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import test.edney.picpay.databinding.FragmentCardRegisterBinding
import test.edney.picpay.view.payment.PaymentActivity

class CardRegisterFragment : Fragment() {

    private lateinit var binding: FragmentCardRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCardRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ui()
    }

    private fun ui(){
        binding.ui = object : CardRegisterUI{
            override fun actionSave() {
                startActivity(Intent(activity, PaymentActivity::class.java))
                activity?.finish()
            }
        }
    }
}
