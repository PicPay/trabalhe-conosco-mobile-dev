//
//  Transaction.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 18/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

struct Transaction: Codable {
    let id: String
    let date: Date
    let status: Status
    let payment: Payment
    
    enum Status: Int, Codable {
        case approved, declined
    }
}
