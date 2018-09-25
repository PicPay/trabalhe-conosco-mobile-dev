//
//  Card.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 18/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import Foundation
public class Card: NSObject, NSCoding {
    /*
     Classe cartão para transacoes
    */
    
    public var card_name = ""
    public var isMainCard = false
    public let card_number : String
    public let cvv : Int
    public let expiry_date: String
    
    public init(card_number: String, cvv: Int, expiration_date: String) {
        self.card_number = card_number
        self.cvv = cvv
        self.expiry_date = expiration_date
    }
    
    public func card_number_() -> String {
        return card_number
    }
    public func cvv_() -> Int {
        return cvv
    }
    public func expiry_date_() -> String {
        return expiry_date
    }
    public func getCardName() -> String? {
        return card_name
    }
    
    // Coding e Decoding
    
    public func encode(with aCoder: NSCoder) {
        aCoder.encode(card_number, forKey: "card_number")
        aCoder.encode(cvv, forKey: "cvv")
        aCoder.encode(expiry_date, forKey: "expiry_date")
        aCoder.encode(isMainCard, forKey: "isMainCard")
        aCoder.encode(card_name, forKey: "card_name")
    }
    
    
    public required convenience init?(coder aDecoder: NSCoder) {
        let card_number = aDecoder.decodeObject(forKey: "card_number") as! String
        let cvv = aDecoder.decodeInteger(forKey: "cvv")
        let expiry_date = aDecoder.decodeObject(forKey: "expiry_date") as! String
        let isMainCard = aDecoder.decodeBool(forKey: "isMainCard")
        let card_name = aDecoder.decodeObject(forKey: "card_name") as! String
        self.init(card_number: card_number, cvv: cvv, expiration_date: expiry_date)
        self.isMainCard = isMainCard
        self.card_name = card_name
    }
}
