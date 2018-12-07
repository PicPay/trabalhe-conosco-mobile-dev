package test.edney.picpay.view.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityCardBinding
import test.edney.picpay.view.card.fragment.CardIntroFragment

class CardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_card)
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
