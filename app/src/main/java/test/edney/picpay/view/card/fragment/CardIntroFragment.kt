package test.edney.picpay.view.card.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import test.edney.picpay.R
import test.edney.picpay.databinding.FragmentCardIntroBinding
import test.edney.picpay.util.ExtrasName

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

      private fun ui() {
            binding.ui = object : CardIntroUI {
                  override fun actionRegister() {
                        val fragment = CardRegisterFragment()
                        val args = Bundle()
                        val transaction = activity?.supportFragmentManager?.beginTransaction()

                        if (userJson != null && transaction != null) {
                              args.putString(ExtrasName.user, userJson)
                              fragment.arguments = args
                              transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                              transaction.replace(R.id.card_frame, fragment).commit()
                        }
                  }
            }
      }

      private fun loadArgs() {
            if (arguments != null)
                  userJson = arguments?.getString(ExtrasName.user)
      }
}
