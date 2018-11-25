//
//  TransactionProviderProtocol.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public protocol TransactionProviderProtocol {
    func process(payment transaction: Transaction, completion: @escaping (ApiResult<Data>) -> Void)
}
