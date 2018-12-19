package test.edney.picpay.view.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityCardBinding
import test.edney.picpay.util.ExtrasName
import test.edney.picpay.view.card.fragment.CardIntroFragment
import test.edney.picpay.view.card.fragment.CardRegisterFragment

class CardActivity : AppCompatActivity() {

      private lateinit var binding: ActivityCardBinding

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_card)
            ui()

            configureFragment()
      }

      private fun ui() {
            binding.ui = object : CardUI {
                  override fun actionBack() {
                        finish()
                  }
            }
      }

      private fun configureFragment() {
            val userJson = intent.getStringExtra(ExtrasName.user)

            if (userJson != null ) {
                  val hasCard = intent.getBooleanExtra(ExtrasName.has_card, false)
                  val args = Bundle()

                  args.putString(ExtrasName.user, userJson)

                  if (hasCard)
                        selectFragment(CardRegisterFragment(), args)
                  else
                        selectFragment(CardIntroFragment(), args)
            } else
                  selectFragment(CardIntroFragment(), null)
      }

      private fun selectFragment(fragment: Fragment, args: Bundle?) {
            if (args != null)
                  fragment.arguments = args
            supportFragmentManager.beginTransaction().replace(R.id.card_frame, fragment).commit()
      }
}
