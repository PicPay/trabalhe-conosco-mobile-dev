package br.com.picpay.picpay.utils

class Validations {
    companion object {
        fun validateCardExpiryDate(expiryDate: String): Boolean {
            return expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}".toRegex())
        }
    }
}