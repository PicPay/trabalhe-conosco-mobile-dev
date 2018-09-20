package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model

import com.google.gson.annotations.SerializedName

data class Transaction(@SerializedName("success") val sucesso: Boolean, @SerializedName("status") val status: String)