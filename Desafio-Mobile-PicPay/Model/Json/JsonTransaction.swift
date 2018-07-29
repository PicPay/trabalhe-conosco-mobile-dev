//
//  Transaction.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 28/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import Foundation

struct JsonTransaction:Codable {
    let id: Int
    let timestamp: Double
    let value: Double
    let destination_user: Contact
    let success: Bool
    let status:String
}
