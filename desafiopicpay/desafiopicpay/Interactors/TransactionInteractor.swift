//
//  TransactionInteractor.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 23/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class TransactionInteractor {
    
    
    func send(transaction: SendTransaction, success: @escaping ((TransactionInfo) -> Void), failure:@escaping ((Error) -> Void)) {
        let service = TransactionService()
        
        RKLoading.showLoading()
        service.send(transaction: transaction) { (data, response, error) in
            RKLoading.hideLoading()
            
            guard error == nil else {
                failure(error!)
                return
            }
            
            if let httpResponse = response as? HTTPURLResponse, !acceptableStatusCodes.contains(httpResponse.statusCode) {
                let error = ErrorHelper.createError(errorCode: httpResponse.statusCode, message: ErrorHelper.message.transactionError)
                failure(error)
            }
            
            do {
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                let transaction = try decoder.decode(Transaction.self, from: data!)
                success(transaction.transactionInfo ?? TransactionInfo())
            } catch {
                let error = ErrorHelper.createError(errorCode: ErrorHelper.code.undefined, message: ErrorHelper.message.undefined)
                failure(error)
            }
        }
    }
    
}
