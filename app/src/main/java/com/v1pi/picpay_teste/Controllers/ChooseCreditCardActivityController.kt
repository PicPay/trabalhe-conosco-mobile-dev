package com.v1pi.picpay_teste.Controllers

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import com.v1pi.picpay_teste.ChooseCreditCardActivity
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Utils.ListCreditCardManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class ChooseCreditCardActivityController(private val activity : ChooseCreditCardActivity) {
    private val listManager = ListCreditCardManager(activity)
    private val databaseManager: DatabaseManager? = DatabaseManager.getInstance(activity)

    fun setUpCreditCardsFromDb() {
        val intent = activity.intent

        // Efeito visual para selecionar o q ele ja havia selecionada na tela anterior
        if (intent != null)
            getCreditCardsFromDb(intent.getIntExtra("uid", -1))
        else
            getCreditCardsFromDb()

    }

    private fun getCreditCardsFromDb(id : Int = -1){
        Log.i("LOGGER", "fui chamado")
        listManager.clear()

        databaseManager?.creditCardDao()?.getAll()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Consumer<List<CreditCard>> {
                    override fun accept(t: List<CreditCard>) {
                        t.map {
                            listManager.insertNewItem(it)
                            if(id >= 0 && id == it.uid) {
                                listManager.selectItem(listManager.getIndexFromId(id))
                            }
                        }
                    }
                })
    }

    fun setResultParams(){
        if(listManager.selectedCreditCard() == null && listManager.size != 0)
            listManager.selectItem(0)


        listManager.selectedCreditCard()?.let {
            val intent = Intent()
            intent.putExtra("number", it.number)
            intent.putExtra("uid", it.uid)
            intent.putExtra("cvv", it.cvv)
            intent.putExtra("expiry_date", it.expiry_date)
            listManager.clear()
            activity.setResult(RESULT_OK, intent)
            return

        }
        activity.setResult(RESULT_OK, null)
    }
}