package com.v1pi.picpay_teste.Utils

class RESTState {

    enum class STATES {
        READY, PROGRESS, INITIATE
    }
    companion object {
        var RESPONSE_USER = STATES.INITIATE
        var REQUEST_TRANSACTION = STATES.INITIATE
    }
}