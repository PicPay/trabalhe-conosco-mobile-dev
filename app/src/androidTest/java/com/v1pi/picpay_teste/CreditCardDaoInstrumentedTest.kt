package com.v1pi.picpay_teste

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Domains.User
import io.reactivex.functions.Predicate
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreditCardDaoInstrumentedTest {
    private val user = User(1001, "Eduardo Santos", "https://randomuser.me/api/portraits/men/9.jpg", "@eduardo.santos")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val creditCard = CreditCard(52, "1111 1111 1111 1111", 700, "12/23")

    lateinit var mDatabase : DatabaseManager

    @Before
    fun setUp() {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), DatabaseManager::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun setDown() {
        DatabaseManager.destroyInstance()
    }

    @Test
    fun getCreditCardWhenNoUserInserted() {
        mDatabase.creditCardDao().getAll()
                .test()
                .assertValue(object : Predicate<List<CreditCard>> {
                    override fun test(t: List<CreditCard>): Boolean {
                        return t.isEmpty()
                    }
                })
    }

    @Test
    fun insertAndGetCreditCardById() {
        mDatabase.creditCardDao().insert(creditCard)

        mDatabase.creditCardDao()
                .findById(creditCard.uid)
                .test()
                .assertValue(object : Predicate<CreditCard> {
                    override fun test(t: CreditCard): Boolean {
                        return creditCard == t
                    }
                })

        mDatabase.creditCardDao().deleteTable()
    }

    @Test
    fun deleteAllCreditCards() {
        mDatabase.creditCardDao().insert(creditCard)

        mDatabase.creditCardDao().deleteTable()

        mDatabase.creditCardDao().getAll().firstElement()
                .test()
                .assertValue(object : Predicate<List<CreditCard>> {
                    override fun test(t: List<CreditCard>): Boolean {
                        return t.isEmpty()
                    }
                })
    }
}