package com.picpay.david.davidrockpicpay.extensions

import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher

object MaskEditUtil {

    val FORMAT_CPF = "###.###.###-##"
    val FORMAT_FONE = "(###)####-#####"
    val FORMAT_CEP = "#####-###"
    val FORMAT_DATE = "##/####"
    val FORMAT_HOUR = "##:##"
    val FORMAT_CREDIT_CARD = "#### #### #### ####"
    val FORMAT_CC_CSC = "###"

    /**
     * Método que deve ser chamado para realizar a formatação
     *
     * @param ediTxt
     * @param mask
     * @return
     */
    fun mask(ediTxt: TextInputEditText, mask: String): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = MaskEditUtil.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }
        }
    }

    fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "").replace("[(]".toRegex(), "").replace("[ ]".toRegex(), "").replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }
}