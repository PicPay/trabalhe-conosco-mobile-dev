//
//  BaseModelProtocol.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public typealias JSON = [String: Any]

public protocol BaseModelProtocol: Codable {
    func toDictionary() -> JSON
}
