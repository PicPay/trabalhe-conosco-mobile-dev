package test.edney.picpay.view.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityCardBinding
import test.edney.picpay.view.card.fragment.CardIntroFragment
import test.edney.picpay.view.card.fragment.CardRegisterFragment
import test.edney.picpay.viewmodel.CardVM

class CardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardBinding
    private lateinit var viewmodel: CardVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_card)
        viewmodel = ViewModelProviders.of(this).get(CardVM::class.java)
        ui()

        configureFragment()
    }

    private fun ui(){
        binding.ui = object : CardUI{
            override fun actionBack() {

            }
        }
    }

    fun configureFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.card_frame, CardIntroFragment()).commit()
    }
}
