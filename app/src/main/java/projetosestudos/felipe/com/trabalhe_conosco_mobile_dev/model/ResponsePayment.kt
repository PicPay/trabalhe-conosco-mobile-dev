package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model

import com.google.gson.annotations.SerializedName

data class ResponsePayment(@SerializedName("transaction") val transaction: Transaction)