package com.jvtnascimento.picpay.view.components

import android.content.Context
import android.widget.Toast
import com.google.gson.JsonSyntaxException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException

class Toaster {
    companion object {
        fun showMessage(message: String, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun showError(message: String, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun showError(error: Throwable, context: Context) {
            try {
                var message = "Ops, algo deu errado. Por favor, tente novamente mais tarde."
                if (error is ConnectException) {
                    message = "Erro ao conectar. Por favor, verifique sua conexão com a internet."
                } else if (error is JsonSyntaxException) {
                    message = error.getLocalizedMessage()
                } else if (error is HttpException) {
                    val errorBody = error.response().errorBody()
                    message = if (errorBody != null) errorBody.string() else message

                    if (isJSONValid(message)) {
                        val json = JSONObject(message)
                        if (json.has("message")) {
                            message = json.getString("message")
                        }
                    } else {
                        message = error.getLocalizedMessage()
                    }
                }

                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        private fun isJSONValid(str: String): Boolean {
            try {
                JSONObject(str)
            } catch (ex: JSONException) {
                try {
                    JSONArray(str)
                } catch (ex1: JSONException) {
                    return false
                }

            }

            return true
        }
    }
}