package com.v1pi.picpay_teste

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Paths


object RestServiceTestHelper {

    @Throws(Exception::class)
    private fun convertStreamToString(inputS: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputS))
        val sb = StringBuilder()
        var line = reader.readLine()

        while (line != null) {
            sb.append(line).append("\n")
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }

    @Throws(Exception::class)
    fun getStringFromFile(filePath: String): String {
        val fullPath = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\java\\com\\v1pi\\picpay_teste" + filePath
        val stream = File(fullPath).inputStream()

        val ret = convertStreamToString(stream)
        stream.close()
        return ret
    }
}