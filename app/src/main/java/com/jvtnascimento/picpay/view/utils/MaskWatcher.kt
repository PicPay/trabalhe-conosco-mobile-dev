package com.jvtnascimento.picpay.view.utils

import android.text.Editable
import android.text.TextWatcher

class MaskWatcher(mask: String): TextWatcher {
    private var isRunning = false
    private var isDeleting = false
    private var mask: String = mask

    companion object {
        fun buildCpf(): MaskWatcher {
            return MaskWatcher("###.###.###-##")
        }

        fun buildTelephone(): MaskWatcher {
            return MaskWatcher("## #####-####")
        }

        fun buildDate(): MaskWatcher {
            return MaskWatcher("##/##/####")
        }
    }


    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        if (isRunning || isDeleting) {
            return
        }
        isRunning = true

        val editableLength = editable.length
        if (editableLength < mask.length) {
            if (mask[editableLength] != '#') {
                editable.append(mask[editableLength])
            } else if (mask[editableLength - 1] != '#') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
        }

        isRunning = false
    }
}