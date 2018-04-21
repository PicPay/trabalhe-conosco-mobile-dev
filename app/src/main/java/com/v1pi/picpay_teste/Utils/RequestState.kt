package com.v1pi.picpay_teste.Utils

class RequestState {

    enum class STATES {
        READY, PROGRESS, INITIATE
    }
    companion object {
        var REQUEST_USER = STATES.INITIATE
    }
}