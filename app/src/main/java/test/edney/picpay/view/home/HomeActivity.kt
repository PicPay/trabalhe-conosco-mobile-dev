package test.edney.picpay.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import test.edney.picpay.R
import test.edney.picpay.view.ReceiptDialog
import test.edney.picpay.databinding.ActivityHomeBinding
import test.edney.picpay.model.UserModel
import test.edney.picpay.view.payment.PaymentActivity
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.animation.LayoutTransition
import test.edney.picpay.util.ExtrasName
import test.edney.picpay.view.card.CardActivity
import test.edney.picpay.viewmodel.HomeVM

class HomeActivity : AppCompatActivity() {

      private lateinit var binding: ActivityHomeBinding
      private lateinit var viewmodel: HomeVM
      private lateinit var mUserAdapter: UserAdapter
      private val receiptDelay = 400L
      private val mGson = Gson()

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
            viewmodel()
            ui()

            showReceipt()
            configureSearch()
            configureListOfUser()
      }

      private fun viewmodel() {
            viewmodel = ViewModelProviders.of(this).get(HomeVM::class.java)

            viewmodel.userResponse.observe(this, Observer {
                  if (it != null) {
                        mUserAdapter.postUsers(it)
                        stopLoading()
                  } else {
                        Toast.makeText(this@HomeActivity, getString(R.string.s_main_message_no_user), Toast.LENGTH_LONG)
                              .show()
                  }
            })
      }

      private fun ui(){
            binding.ui = object : HomeUI{
                  override fun actionSearch() {
                       if(!binding.etSearch.isFocused)
                             binding.etSearch.requestFocus()
                  }
                  override fun actionCloseSearch() {
                        binding.etSearch.setText("")
                  }
            }
      }

      private fun configureSearch(){
            binding.llSearch.layoutTransition = LayoutTransition()

            binding.etSearch.addTextChangedListener(object : TextWatcher {
                  override fun afterTextChanged(s: Editable?) {
                        val text = s.toString()

                        mUserAdapter.getFilter().filter(text)
                  }
                  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
                  val layout = RelativeLayout.LayoutParams(
                        if(hasFocus) RelativeLayout.LayoutParams.MATCH_PARENT
                        else RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                  )

                  binding.llSearch.layoutParams = layout
                  binding.llSearch.requestLayout()

                  binding.rlSearch.setBackgroundResource(
                        if(hasFocus) R.drawable.edittext_focused else R.drawable.edittext
                  )

                  binding.ivSearch.setColorFilter(ContextCompat.getColor(this,
                        if(hasFocus) R.color.colorWhite else R.color.colorSearchText)
                  )

                  binding.ivSearchClose.visibility = if(hasFocus) VISIBLE else GONE
            }
            binding.etSearch.setOnKeyListener { _, keyCode, event ->
                  var stop = false

                  if (keyCode == KeyEvent.KEYCODE_BACK
                        && event?.action == KeyEvent.ACTION_UP
                  ) {
                        removeSearchFocus()
                        stop = true
                  }
                  stop
            }
      }

      private fun showReceipt() {
            val args = intent.getStringExtra(ExtrasName.transaction)

            if (args != null) {
                  val dialog = ReceiptDialog()
                  val argsToDialog = Bundle()
                  val cardNumber = intent.getStringExtra(ExtrasName.card_number)

                  if(cardNumber != null)
                        argsToDialog.putString(ExtrasName.card_number, cardNumber)
                  argsToDialog.putString(ExtrasName.transaction, args)
                  dialog.arguments = argsToDialog
                  Handler().postDelayed({ dialog.show(supportFragmentManager, "receipt_dialog") }, receiptDelay)
            }
      }

      private fun configureListOfUser() {
            val animation = AnimationUtils.loadLayoutAnimation(this@HomeActivity, R.anim.layout_anim)

            mUserAdapter = UserAdapter(object : UserAdapterListener {
                  override fun onItemClick(user: UserModel) {
                        val hasCard = viewmodel.hasCard()
                        val intent: Intent

                        if(hasCard){
                              intent = Intent(this@HomeActivity, PaymentActivity::class.java).apply {
                                    putExtra(ExtrasName.has_card, hasCard)
                                    putExtra(ExtrasName.user, mGson.toJson(user))
                              }
                        }
                        else{
                              intent = Intent(this@HomeActivity, CardActivity::class.java).apply {
                                    putExtra(ExtrasName.has_card, hasCard)
                                    putExtra(ExtrasName.user, mGson.toJson(user))
                              }
                        }
                        startActivity(intent)
                  }
            })
            binding.rvUser.setHasFixedSize(true)
            binding.rvUser.layoutAnimation = animation
            binding.rvUser.layoutManager = LinearLayoutManager(this)
            binding.rvUser.adapter = mUserAdapter
            viewmodel.requestUsers()
      }

      private fun stopLoading() {
            binding.progress.visibility = GONE
            binding.rvUser.visibility = VISIBLE
      }

      private fun removeSearchFocus(){
            binding.etSearch.clearFocus()
            if (currentFocus != null) {
                  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                  imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
      }
}