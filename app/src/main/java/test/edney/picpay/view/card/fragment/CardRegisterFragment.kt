package test.edney.picpay.view.card.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import test.edney.picpay.database.CardEntity
import test.edney.picpay.databinding.FragmentCardRegisterBinding
import test.edney.picpay.util.AppUtil
import test.edney.picpay.util.ExtrasName
import test.edney.picpay.util.MyLog
import test.edney.picpay.view.payment.PaymentActivity
import test.edney.picpay.viewmodel.CardRegisterVM

class CardRegisterFragment : Fragment() {

      private val log = MyLog(CardRegisterFragment::class.java.simpleName)
      private lateinit var binding: FragmentCardRegisterBinding
      private lateinit var viewmodel: CardRegisterVM
      private var userJson: String? = null
      private var canRegister = MutableLiveData<BooleanArray>()

      override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            binding = FragmentCardRegisterBinding.inflate(inflater, container, false)

            return binding.root
      }

      override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            loadArgs()
            viewmodel()
            ui()
            configureInputs()
            observeValues()
      }

      private fun viewmodel() {

            viewmodel = ViewModelProviders.of(this).get(CardRegisterVM::class.java)

            viewmodel.card.observe(this, Observer {
                  if (it != null) {
                        binding.tvNumber.setText(it.number)
                        binding.tvName.setText(it.name)
                        binding.tvExpiration.setText(it.expiration)
                        binding.tvCvv.setText(it.cvv)
                  }
            })
      }

      private fun ui() {
            binding.ui = object : CardRegisterUI {
                  override fun actionSave() {
                        val card = CardEntity()
                        val intent = Intent(activity, PaymentActivity::class.java)

                        card.number = binding.tvNumber.text.toString()
                        card.name = binding.tvName.text.toString()
                        card.expiration = binding.tvExpiration.text.toString()
                        card.cvv = binding.tvCvv.text.toString()
                        viewmodel.addCard(card)

                        intent.putExtra(ExtrasName.user, userJson)
                        startActivity(intent)
                        activity?.finish()
                  }
            }
      }

      private fun observeValues(){
            canRegister.observe(this, Observer {
                  val isVisible = binding.btRegister.visibility

                  if(it != null){
                        log.showD("observeValues", "canRegister", it)
                        if(it[0] && it[1] && it[2] && it[3] && isVisible != View.VISIBLE)
                              binding.btRegister.visibility = View.VISIBLE
                        else if(isVisible != View.GONE)
                              binding.btRegister.visibility = View.GONE
                  }
            })
      }

      private fun configureInputs() {
            canRegister.value = BooleanArray(4){false}
            binding.tvNumber.addTextChangedListener(object: TextWatcher{
                  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                  override fun afterTextChanged(s: Editable?) {
                        val checkArray = canRegister.value
                        val text = s?.toString()

                        binding.tvNumber.removeTextChangedListener(this)
                        if(text != null ){
                              val formated = AppUtil.formatCardNumber(text)

                              checkArray!![0] = formated.length == 19
                              binding.tvNumber.setText(formated)
                              binding.tvNumber.setSelection(formated.length)
                        }
                        else
                              checkArray!![0] = false

                        binding.tvNumber.addTextChangedListener(this)
                        canRegister.value = checkArray
                  }
            })
            binding.tvName.addTextChangedListener(object : TextWatcher{
                  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                  override fun afterTextChanged(s: Editable?) {
                        val checkArray = canRegister.value
                        val text = s?.toString()

                        checkArray!![1] = text != null && text.isNotEmpty()
                        canRegister.value = checkArray
                  }
            })
            binding.tvExpiration.addTextChangedListener(object: TextWatcher{
                  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                  override fun afterTextChanged(s: Editable?) {
                        val checkArray = canRegister.value
                        val text = s?.toString()

                        binding.tvExpiration.removeTextChangedListener(this)
                        if(text != null ){
                              val formated = AppUtil.formartDate(text)

                              checkArray!![2] = formated.length == 5
                              binding.tvExpiration.setText(formated)
                              binding.tvExpiration.setSelection(formated.length)
                        }
                        else
                              checkArray!![2] = false

                        binding.tvExpiration.addTextChangedListener(this)
                        canRegister.value = checkArray
                  }
            })
            binding.tvCvv.addTextChangedListener(object : TextWatcher {
                  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                  override fun afterTextChanged(s: Editable?) {
                        val checkArray = canRegister.value
                        val text = s?.toString()

                        checkArray!![3] = text != null && text.length == 3
                        canRegister.value = checkArray
                  }
            })
      }

      private fun loadArgs() {
            if (arguments != null)
                  userJson = arguments?.getString(ExtrasName.user)
      }
}