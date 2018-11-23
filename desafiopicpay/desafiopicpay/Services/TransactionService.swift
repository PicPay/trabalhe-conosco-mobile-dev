//
//  TransactionService.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 23/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class TransactionService {
    
    func send(transaction: SendTransaction, completionHandler:@escaping( (Data?, URLResponse?, Error?) -> Void )) {
        let uri = URL(string: "http://careers.picpay.com/tests/mobdev/transaction")
        
        let headers: [String: String] = ["Content-Type": "application/json"]
        
        var request = URLRequest(url: uri!)
        request.allHTTPHeaderFields = headers
        request.httpMethod = HTTPMethods.post        
        request.httpBody = transaction.data(encodeStrategy: .convertToSnakeCase)
        
        let task = URLSession.shared.dataTask(with: request, completionHandler: completionHandler)
        task.resume()
    }
    
}
