package com.michaeljordan.testemobilepicpay.repository

import com.michaeljordan.testemobilepicpay.data.remote.PicPayApi
import com.michaeljordan.testemobilepicpay.data.remote.RetrofitFactory
import com.michaeljordan.testemobilepicpay.model.Contact
import retrofit2.Call

class ContactRepository {
    private val service: PicPayApi = RetrofitFactory.create()

    fun getContacts() : Call<List<Contact>> {
        return service.getUsers()
    }

    object ContactRepositoryProvider {
        fun provideContactRepository(): ContactRepository {
            return ContactRepository()
        }
    }
}