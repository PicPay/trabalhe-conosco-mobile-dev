//
//  ProviderProtocol.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public enum ApiResult<T> {
    case success(T)
    case failure(Error)
}

public enum DAOResult {
    case success
    case failure(Error)
}

public typealias DAOCompletion = (DAOResult) -> Void

public protocol ProviderProtocol {
    
}
