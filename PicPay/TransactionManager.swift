//
//  TransactionManager.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class TransactionManager: NSObject {
    fileprivate let business: TransactionBusiness = TransactionBusiness()
    
    public func process(payment transaction: Transaction, completion: @escaping (ApiResult<Data>) -> Void) {
        OperationQueue.main.addOperation { [weak self] in
            self?.business.process(payment: transaction, completion: completion)
        }
    }
}
