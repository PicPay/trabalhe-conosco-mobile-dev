package test.edney.picpay.util

import java.util.*

object AppUtil {

      fun getPaymentValue(text: String): Double {
            val noPeriod = text.replace(".", "")
            val replaced = noPeriod.replace(",", ".")

            return replaced.toDouble()
      }

      fun formartCurrencyNumber(text: String): Int {
            val noPeriod = text.replace(".", "")
            val noComma = noPeriod.replace(",", "")

            return noComma.toInt()
      }

      fun formatCurrency(text: String): String {
            val noPeriod = text.replace(".", "")
            val noComma = noPeriod.replace(",", "")
            val clean = noComma.toInt().toString()
            var formated = if (clean.length == 1) "0,0" else if (clean.length == 2) "0" else ""

            for (i in 0 until clean.length) {
                  if ((clean.length - 2) == i)
                        formated += ","

                  formated += clean[i]

                  if ((clean.length - 6) == i)
                        formated += "."
            }

            return formated
      }

      fun formartDate(text: String): String{
            val clean = text.replace("/", "")
            var formated = ""

            for(i in 0 until clean.length){
                  if(i == 2)
                        formated += "/"
                  formated += clean[i]
            }

            return formated
      }

      fun formatCardNumber(text: String): String{
            val clean = text.replace(" ", "")
            var formated = ""

            for(i in 0 until clean.length){
                  if(i == 4 || i == 8 || i == 12)
                        formated += " "
                  formated += clean[i]
            }

            return formated
      }

      fun formatTimeStamp(time: Long?): String?{
            if(time != null) {
                  val c = Calendar.getInstance()

                  c.timeInMillis = time

                  val day = c.get(Calendar.DAY_OF_MONTH)
                  val month = c.get(Calendar.MONTH) + 1
                  val year = c.get(Calendar.YEAR)
                  val hour = c.get(Calendar.HOUR_OF_DAY)
                  val min = c.get(Calendar.MINUTE)

                  return "$day/${if(month < 10) "0$month" else month.toString()}/$year às $hour:$min"
            }

            return null
      }
}