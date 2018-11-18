//
//  ApiConnection.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import Foundation

protocol ApiConnectionProtocol {
    
    static func getListagemPessoas(onComplete: @escaping ([PessoasRetornoElement]) -> Void,
                                     onError: @escaping (_ message: String) -> Void)
    
    static func getDetalheTransferencia(number: String, cvv: Int, expiryDate: String, userId: Int, value: Double, onComplete: @escaping (Transferencia) -> Void,
                                   onError: @escaping (_ message: String) -> Void)
}

class ApiConnection: ApiConnectionProtocol {
    
    static func getListagemPessoas(onComplete: @escaping ([PessoasRetornoElement]) -> Void,
                                   onError: @escaping (String) -> Void) {
        if let url = URL(string: "http://careers.picpay.com/tests/mobdev/users") {
            let task = URLSession.shared.pessoasRetornoTask(with: url) { pessoasRetorno, response, error in
                if let pessoasRetorno = pessoasRetorno {
                    onComplete(pessoasRetorno)
                    return
                }
                
                onError(error?.localizedDescription ?? "Não foi possível obter a listagem de pessoas!")
            }
            task.resume()
        }
    }
    
    static func getDetalheTransferencia(number: String, cvv: Int, expiryDate: String, userId: Int, value: Double, onComplete: @escaping (Transferencia) -> Void, onError: @escaping (String) -> Void) {
        
        let Url = String(format: "http://careers.picpay.com/tests/mobdev/transaction")
        guard let serviceUrl = URL(string: Url) else { return }
        let parameterDictionary = ["card_number": number, "cvv": cvv, "value": value,
                                   "expiry_date": expiryDate, "destination_user_id": userId] as [String : Any]
        var request = URLRequest(url: serviceUrl)
        request.httpMethod = "POST"
        request.setValue("Application/json", forHTTPHeaderField: "Content-Type")
        guard let httpBody = try? JSONSerialization.data(withJSONObject: parameterDictionary, options: []) else {
            return
        }
        request.httpBody = httpBody
           let task = URLSession.shared.transferenciaRetornoTask(with: request) { transferenciaRetorno, response, error in
             if let transferenciaRetorno = transferenciaRetorno?.transaction {
               onComplete(transferenciaRetorno)
             }
            
            onError(error?.localizedDescription ?? "Não foi possível concluir a transação")
           }
           task.resume()
    }
}
