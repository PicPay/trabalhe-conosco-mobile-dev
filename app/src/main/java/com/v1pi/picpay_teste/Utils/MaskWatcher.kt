package com.v1pi.picpay_teste.Utils

import android.text.Editable
import android.text.TextWatcher

class MaskWatcher(private val mask : String, private val justDigit : Boolean = true) : TextWatcher {
    private var isRunning = false
    private var isDeleting = false

    override fun afterTextChanged(editable: Editable?) {
        if(isDeleting || isRunning)
            return

        isRunning = true

        editable?.let {e : Editable ->
            val editableLength = e.length

            if(justDigit && !e[editableLength-1].isDigit()) {
                e.delete(editableLength-1, editableLength)
                return@let
            }

            if(editableLength < mask.length) {
                if(mask[editableLength] != '#') {
                    e.append(mask[editableLength])
                } else if (mask[editableLength-1] != '#') {
                    e.insert(editableLength-1, mask, editableLength-1, editableLength)
                }
            }
        }

        isRunning = false
    }

    override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}