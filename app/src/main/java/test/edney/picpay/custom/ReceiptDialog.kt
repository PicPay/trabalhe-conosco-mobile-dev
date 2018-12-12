package test.edney.picpay.custom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import test.edney.picpay.databinding.DialogReceiptBinding
import test.edney.picpay.model.ReceiptModel

class ReceiptDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogReceiptBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogReceiptBinding.inflate(inflater, container, false)
        if(arguments != null) {
            val gson = Gson()
            val jsonString = arguments?.getString("transaction")

            if(jsonString != null){
                val modelData = gson.fromJson(jsonString, ReceiptModel::class.java)

                binding.model = modelData
            }
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bsDialog = it as BottomSheetDialog
            val bs = bsDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bsBehavior = BottomSheetBehavior.from(bs)

            bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }
}