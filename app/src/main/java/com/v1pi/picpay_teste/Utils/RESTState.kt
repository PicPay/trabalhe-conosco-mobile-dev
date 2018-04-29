package com.v1pi.picpay_teste.Utils

// Classe de estado utilizada somente para os testes
class RESTState {

    enum class STATES {
        READY, PROGRESS, INITIATE
    }
    companion object {
        var RESPONSE_USER = STATES.INITIATE
        var REQUEST_TRANSACTION = STATES.INITIATE
    }
}