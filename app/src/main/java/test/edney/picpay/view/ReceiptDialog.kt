package test.edney.picpay.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import test.edney.picpay.R
import test.edney.picpay.databinding.DialogReceiptBinding
import test.edney.picpay.util.ExtrasName
import test.edney.picpay.viewmodel.ReceiptDialogVM

class ReceiptDialog : BottomSheetDialogFragment() {

      private lateinit var binding: DialogReceiptBinding
      private lateinit var viewmodel: ReceiptDialogVM

      override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            binding = DialogReceiptBinding.inflate(inflater, container, false)
            viewmodel = ViewModelProviders.of(this).get(ReceiptDialogVM::class.java)
            model()

            return binding.root
      }

      override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

            dialog.setOnShowListener {
                  val bsDialog = it as BottomSheetDialog
                  val bs = bsDialog.
                        findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                  val bsBehavior = BottomSheetBehavior.from(bs)

                  bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            return dialog
      }

      private fun model(){
            if (arguments != null) {
                  val jsonData = arguments?.getString(ExtrasName.transaction)
                  val cardNumber = arguments?.getString(ExtrasName.card_number)

                  if (jsonData != null) {
                        val model = viewmodel.getReceiptFromJSON(jsonData, cardNumber)

                        if(model != null)
                              binding.model = model
                        else{
                              Toast.makeText(context, getString(R.string.s_receipt_internal_erro), Toast.LENGTH_SHORT).show()
                              dialog.dismiss()
                        }
                  }
                  else {
                        Toast.makeText(context, getString(R.string.s_receipt_erro), Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                  }
            }
      }
}