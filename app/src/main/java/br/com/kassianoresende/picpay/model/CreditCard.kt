package br.com.kassianoresende.picpay.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "credit_card")
class CreditCard (
            @PrimaryKey(autoGenerate = true)
            val id:Long ,
            val cardNumber:String,
            val flag:String,
            val name:String,
            val dueDate:String,
            val cvv:Int)