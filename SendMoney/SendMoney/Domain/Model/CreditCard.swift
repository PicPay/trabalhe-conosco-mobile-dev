//
//  CreditCard.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 29/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import ObjectMapper
import RealmSwift

class CreditCard: Object, Mappable {
    dynamic var cardNumber: String = ""
    dynamic var cvv: Int = 0
    dynamic var expiryDate: String = ""
    var usingToPay: Bool = false
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        cardNumber          <- map["card_number"]
        cvv                 <- map["cvv"]
        expiryDate          <- map["expiry_date"]
    }
}
