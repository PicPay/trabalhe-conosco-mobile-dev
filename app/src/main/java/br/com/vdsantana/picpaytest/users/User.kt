package br.com.vdsantana.picpaytest.users

import java.io.Serializable

/**
 * Created by vd_sa on 30/03/2018.
 */

data class User(val id: Int, val name: String, val img: String, val username: String) : Serializable
