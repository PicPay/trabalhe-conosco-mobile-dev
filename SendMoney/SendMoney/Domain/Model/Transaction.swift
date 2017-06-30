//
//  Transaction.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 28/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import ObjectMapper
import RealmSwift

class Transaction: Object, Mappable {
    
    dynamic var id: Int = 0
    dynamic var cardNumber: String = ""
    dynamic var cvv: Int = 0
    dynamic var expiryDate: String = ""
    dynamic var destinationUserId: Int = 0
    dynamic var valueSent: Double = 0.0
    
    override static func primaryKey() -> String? {
        return "id"
    }
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        id                  <- map["id"]
        cardNumber          <- map["card_number"]
        cvv                 <- map["cvv"]
        expiryDate          <- map["expiry_date"]
        destinationUserId   <- map["destination_user_id"]
        valueSent           <- map["value"]
    }
}
