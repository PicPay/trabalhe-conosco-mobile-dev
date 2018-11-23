//
//  ErrorHelper.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class ErrorHelper {
    private static let domainApp = "Desafio PicPay"
    
    struct code {
        static let undefined            = -1050
        static let noInternetConnection = -1051
    }
    
    struct title {
        static let `default` = "Desafio PicPay"
        static let sessionExpired = "Sua sessão expirou"
    }
    
    struct message {
        static let undefined = "Ocorreu um problema não identificado, por favor tente mais tarde."
        static let noInternetConnection = "Problema de conexão com a internet, verifique e tente novamente."
        static let listUserError = "Ocorreu um problema ao listar pessoas."
        static let transactionError = "Ocorreu um problema ao enviar transação."
    }
    
    static func createGenericError(message: String) -> NSError {
        let error = NSError(domain: domainApp, code: code.undefined, userInfo: [NSLocalizedDescriptionKey: message])
        
        return error
    }
    
    static func createError(errorCode: Int, message: String) -> NSError {
        let error = NSError(domain: domainApp, code: errorCode, userInfo: [NSLocalizedDescriptionKey: message])
        
        return error
    }
    
    static func createError(errorCode: Int, messages: [String: Any]) -> NSError {
        let error = NSError(domain: domainApp, code: errorCode, userInfo: messages)
        
        return error
    }
    
}
