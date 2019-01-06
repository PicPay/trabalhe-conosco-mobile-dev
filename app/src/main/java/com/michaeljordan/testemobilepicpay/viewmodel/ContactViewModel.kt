package com.michaeljordan.testemobilepicpay.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.michaeljordan.testemobilepicpay.model.Contact
import com.michaeljordan.testemobilepicpay.repository.ContactRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContactRepository.ContactRepositoryProvider.provideContactRepository()
    private lateinit var contactsResult: MutableLiveData<List<Contact>>

    fun getContactsObservable(): MutableLiveData<List<Contact>> {
        if (!::contactsResult.isInitialized) {
            contactsResult = MutableLiveData()
        }
        return contactsResult
    }

    fun getContacts() {
        val callContact = repository.getContacts()

        callContact.enqueue(object : Callback<List<Contact>> {
            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                Log.d("PicPay", "Error: ${t.message}")
            }

            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                contactsResult.value = response.body()
            }

        })
    }
}