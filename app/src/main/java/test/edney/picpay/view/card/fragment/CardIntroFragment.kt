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
    private var userJson: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCardIntroBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadArgs()
        ui()
    }

    private fun ui(){
        binding.ui = object : CardIntroUI{
            override fun actionBack() {
                activity?.finish()
            }
            override fun actionRegister() {
                val fragment = CardRegisterFragment()
                val args = Bundle()

                if(userJson != null){
                    args.putString("user", userJson)
                    fragment.arguments = args

                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.card_frame, fragment)
                        ?.commit()
                }
            }
        }
    }

    private fun loadArgs(){
        if(arguments != null)
            userJson = arguments?.getString("user")
    }
}
