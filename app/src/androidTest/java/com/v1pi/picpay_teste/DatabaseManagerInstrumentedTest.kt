package com.v1pi.picpay_teste

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Utils.DbWorkerThread
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DatabaseManagerInstrumentedTest {
    private val user = User(1001, "Eduardo Santos", "https://randomuser.me/api/portraits/men/9.jpg", "@eduardo.santos")

    private lateinit var mDbWorkerThread: DbWorkerThread
    private var dm: DatabaseManager? = null

    @get: Rule
    val activityTestRule : ActivityTestRule<PaymentMethodActivity> = object : ActivityTestRule<PaymentMethodActivity>(PaymentMethodActivity::class.java) {
        override fun getActivityIntent() : Intent {
            val intent = Intent()

            // O start dela tem um delay para inicializara variavel handle dentro da classe, por isso tem que ser iniciado aqui
            mDbWorkerThread = DbWorkerThread("dbWorkerThreadTest")
            mDbWorkerThread.start()

            intent.putExtra("name", user.name)
            intent.putExtra("username", user.username)
            intent.putExtra("id", user.id.toString())
            intent.putExtra("img", user.img)
            return intent
        }
    }

    @Before
    fun setUp() {
        dm = DatabaseManager.getInstance(activityTestRule.activity)

    }

    @Test
    fun shouldCreateDatabaseManager() {
        Assert.assertTrue(dm != null)
    }

    @Test
    fun shouldInstantiateCreditCardDao() {
        Assert.assertTrue(dm?.creditCardDao() != null)
    }

    @Test
    fun shouldGetAllCreditCard(){
        val task = Runnable {
            val listCreditCard = dm?.creditCardDao()?.getAll()

            Assert.assertTrue(listCreditCard != null)

        }
        mDbWorkerThread.postTask(task)
    }
}