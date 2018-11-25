//
//  TransactionProvider.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class TransactionProvider: TransactionProviderProtocol {
    public func process(payment transaction: Transaction, completion: @escaping (ApiResult<Data>) -> Void) {
        guard Utils.hasConnection else {
            completion(.failure(GenericError.parse("Sem conexão, tente mais tarde")))
            return
        }
        
        let params: URLSessionParameters = (bodyParameters: transaction.toDictionary(), queryParameters: nil)
        Api.transaction.POST(url: "/transaction", parameters: params, header: nil) { result in
            do {
                let data = try result()
                if let response = data {
                    completion(.success(response))
                }
            } catch {
                completion(.failure(error))
            }
        }
    }
}
