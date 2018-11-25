//
//  TransactionBusiness.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class TransactionBusiness {
    
    // Parameters
    fileprivate let provider: TransactionProviderProtocol
    
    init() {
        provider = TransactionProvider()
    }
    
    init(provider: TransactionProviderProtocol) {
        self.provider = provider
    }
    
    public func process(payment transaction: Transaction, completion: @escaping (ApiResult<Data>) -> Void) {
        provider.process(payment: transaction) { result in
            //
        }
    }
}
