package com.v1pi.picpay_teste

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.v1pi.picpay_teste.Domains.Transaction
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Utils.RetrofitConfig
import com.v1pi.picpay_teste.Utils.TransactionDeserializer
import io.reactivex.functions.Predicate
import junit.framework.Assert
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitConfigUnitTest {
    private val server = MockWebServer()
    private var retrofitConfig = RetrofitConfig(server.url("/").toString())
    private val fileSuccessUser = "/assets/response_ok"
    private val fileSuccessTransaction = "/assets/response_ok_transaction"

    @After
    fun finalize() {
        server.shutdown()
    }

    @Test
    fun testRequestUserService() {
        server.enqueue(MockResponse().setBody(RestServiceTestHelper.getStringFromFile(fileSuccessUser)))

        retrofitConfig.userService.users().test()
                .assertComplete()
    }

    @Test
    fun testCountUsers() {
        server.enqueue(MockResponse().setBody(RestServiceTestHelper.getStringFromFile(fileSuccessUser)))

        retrofitConfig.userService.users().test()
                .assertValue(object : Predicate<List<User>> {
                    override fun test(t: List<User>): Boolean {
                        return t.size == 33
                    }
                })
    }


    @Test
    fun testWithDelay() {
        val response = MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(RestServiceTestHelper.getStringFromFile(fileSuccessUser))
        response.throttleBody(1024, 1, TimeUnit.SECONDS)

        server.enqueue(response)

        retrofitConfig.userService.users().test()
                .assertComplete()
    }

    @Test
    fun testPostTransaction() {
        val json = "{\"id\":12314,\"timestamp\":1524863585,\"value\":79.9,\"destination_user\":{\"id\":1002,\"name\":\"Marina Coelho\",\"img\":\"https://randomuser.me/api/portraits/women/37.jpg\",\"username\":\"@marina.coelho\"},\"success\":true,\"status\":\"Aprovada\"}"
        retrofitConfig = RetrofitConfig(server.url("/").toString(), GsonBuilder().registerTypeAdapter(Transaction::class.java, TransactionDeserializer()).create())
        val user = User(1002, "Marina Coelho", "https://randomuser.me/api/portraits/women/37.jpg", "@marina.coelho")
        server.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(json))

        val transaction = Transaction("1111111111111111", 700, 52.2f, "13/20", user)
        val call = retrofitConfig.transactionService.send(transaction)

        call.test()
                .assertValue(object : Predicate<Transaction> {
                    override fun test(t: Transaction): Boolean {
                        return t == Gson().fromJson(json, Transaction::class.java)
                    }
                })
    }
}
