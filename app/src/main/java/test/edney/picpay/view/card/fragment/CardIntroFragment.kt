package test.edney.picpay.view.card.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import test.edney.picpay.R
import test.edney.picpay.databinding.FragmentCardIntroBinding
import test.edney.picpay.view.card.CardIntroUI

class CardIntroFragment : Fragment() {

    private lateinit var binding: FragmentCardIntroBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCardIntroBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ui()
    }

    private fun ui(){
        binding.ui = object : CardIntroUI{
            override fun actionRegister() {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.card_frame, CardRegisterFragment())
                    ?.commit()
            }
        }
    }
}
