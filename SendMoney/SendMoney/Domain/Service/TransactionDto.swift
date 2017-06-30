//
//  TransactionDto.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 29/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import ObjectMapper
import Alamofire
//import AlamofireObjectMapper

class TransactionDto {
    func postTransaction(_ transaction: Transaction,completion: @escaping (_ success: Bool) -> Void, fail: @escaping (_ message: String) -> Void) {
        
        Alamofire.request("http://careers.picpay.com/tests/mobdev/transaction", method: .post, parameters: transaction.toJSON()).response(completionHandler: { response in
            completion(true)
        })
    }
    
    func saveToDatabase(_ transaction: Transaction) {
        let repo = TransactionRepository()
        // Update id property
        transaction.id = repo.incrementId()
        repo.Add(item: transaction)
    }
}
