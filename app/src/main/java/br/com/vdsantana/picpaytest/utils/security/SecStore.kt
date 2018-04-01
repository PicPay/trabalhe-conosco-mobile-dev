package br.com.vdsantana.picpaytest.utils.security

import android.content.Context
import android.util.Log
import br.com.vdsantana.picpaytest.Config
import br.com.vdsantana.picpaytest.creditcard.CreditCard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moldedbits.r2d2.R2d2
import javax.inject.Inject


/**
 * Created by vd_sa on 31/03/2018.
 */
class SecStore @Inject constructor(val mGson: Gson, val mContext: Context) {

    companion object {
        private val CREDIT_CARD_KEY = "credit_card_key2"
    }

    fun saveCreditCard(creditCard: CreditCard) {
        val r2d2 = R2d2(mContext)
        val preferences = mContext.getSharedPreferences(Config.SP_KEY, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val creditCards = retrieveCreditCards()
        creditCards.add(creditCard)
        val encryptedCard = r2d2.encryptData(mGson.toJson(creditCards))
        editor.putString(CREDIT_CARD_KEY, encryptedCard);
        editor.apply();
    }

    fun retrieveCreditCards(): MutableList<CreditCard> {
        val preferences = mContext.getSharedPreferences(Config.SP_KEY, Context.MODE_PRIVATE)
        val r2d2 = R2d2(mContext)
        val encryptedCreditCards = preferences.getString(CREDIT_CARD_KEY, "")

        if (encryptedCreditCards == null || encryptedCreditCards.isEmpty())
            return emptyList<CreditCard>().toMutableList()

        val decryptedCreditCards = r2d2.decryptData(encryptedCreditCards)

        Log.e("valores", "val = " + decryptedCreditCards)

        val type = object : TypeToken<MutableList<CreditCard>>() {}.type

        return mGson.fromJson(decryptedCreditCards, type)
    }
}