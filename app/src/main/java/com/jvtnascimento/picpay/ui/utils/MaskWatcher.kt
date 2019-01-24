package com.jvtnascimento.picpay.ui.utils

import android.text.Editable
import android.text.TextWatcher

class MaskWatcher(private var mask: String): TextWatcher {
    private var isRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        if (this.isRunning || this.isDeleting) {
            return
        }

        this.isRunning = true

        val editableLength = editable.length
        if (editableLength < this.mask.length) {
            if (this.mask[editableLength] != '#') {
                editable.append(this.mask[editableLength])
            } else if (this.mask[editableLength - 1] != '#') {
                editable.insert(editableLength - 1, this.mask, editableLength - 1, editableLength)
            }
        }

        this.isRunning = false
    }
}