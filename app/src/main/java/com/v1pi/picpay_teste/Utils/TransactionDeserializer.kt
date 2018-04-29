package com.v1pi.picpay_teste.Utils

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.v1pi.picpay_teste.Domains.Transaction
import com.v1pi.picpay_teste.Domains.User
import java.lang.reflect.Type

class TransactionDeserializer : JsonDeserializer<Any> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any {
        var element : JsonElement? = json?.asJsonObject

        if(json?.asJsonObject?.get("transaction") != null)
            element = json.asJsonObject?.get("transaction")


        return Gson().fromJson(element, Transaction::class.java)
    }

}