package com.v1pi.picpay_teste

import com.v1pi.picpay_teste.Domains.User
import org.junit.Assert
import org.junit.Test

class UserUnitTest {
    @Test
    fun equalsIsCorrect() {
        Assert.assertEquals(true, User(5) == User(5))
        Assert.assertEquals(false, User(6) == User(7))
    }
}