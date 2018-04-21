package com.v1pi.picpay_teste

import com.v1pi.picpay_teste.Utils.RetrofitConfig
import junit.framework.Assert
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import java.util.concurrent.TimeUnit



class RetrofitConfigUnitTest {
    private val server = MockWebServer()
    private val retrofitConfig = RetrofitConfig(server.url("/").toString())
    private val fileSuccesssUser = "/assets/response_ok"

    @After
    fun finalize() {
        server.shutdown()
    }

    @Test
    fun testRequestUserService() {
        server.enqueue(MockResponse().setBody(RestServiceTestHelper.getStringFromFile(fileSuccesssUser)))

        val call = retrofitConfig.userService.Users()
        Assert.assertTrue(call.execute() != null)
    }

    @Test
    fun testCountUsers() {
        server.enqueue(MockResponse().setBody(RestServiceTestHelper.getStringFromFile(fileSuccesssUser)))

        val call = retrofitConfig.userService.Users()

        Assert.assertEquals(33, call.execute()?.body()?.size)
    }

    @Test
    fun testWithDelay() {
        val response = MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(RestServiceTestHelper.getStringFromFile(fileSuccesssUser))
        response.throttleBody(1024, 1, TimeUnit.SECONDS)

        server.enqueue(response)

        val call = retrofitConfig.userService.Users()
        Assert.assertTrue(call.execute() != null)
    }
}