//
//  Payment.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 13/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

struct Payment: Codable {
    let receiverID: Int
    let receiverName: String
    let cardNumber: String
    let cardExpires: String
    let cardCode: String
    let amount: String

    enum CodingKeys: String, CodingKey {
        case receiverID = "destination_user_id"
        case receiverName = "destination_user"
        case cardNumber = "card_number"
        case cardExpires = "expiry_date"
        case cardCode = "cvv"
        case amount = "value"
    }
}
