//
//  CreditCard.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

struct CreditCard {
    
    var cardNumber: Int
    var cvv: Int
    var expireDate: Date
    
}

extension CreditCard: Codable {
    
    enum CodingKeys: String, CodingKey {
        case cardNumber
        case cvv
        case expireDate
    }
    
    init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        cardNumber = try values.decode(Int.self, forKey: .cardNumber)
        cvv = try values.decode(Int.self, forKey: .cvv)
        let stringDate = try values.decode(String.self, forKey: .expireDate)
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/yy"
        expireDate = dateFormatter.date(from: stringDate)!
    }
    
    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(cardNumber, forKey: .cardNumber)
        try container.encode(cvv, forKey: .cvv)
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/yy"
        try container.encode(dateFormatter.string(from: expireDate), forKey: .expireDate)
    }
}
