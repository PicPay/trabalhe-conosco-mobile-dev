package com.v1pi.picpay_teste

import android.support.test.InstrumentationRegistry
import android.util.Log
import com.v1pi.picpay_teste.Utils.DownloadImageTask
import org.junit.Assert
import org.junit.Test

class DownloadImageTaskInstrumentedTest {

    @Test
    fun shouldDownloadImage() {
        val url = "https://randomuser.me/api/portraits/women/10.jpg"
        //Não uso um WebMock pois quero testar o download no site real e não por imagem na aplicação
        val image = DownloadImageTask(null).execute(url).get()
        Assert.assertTrue(image != null)
    }
}