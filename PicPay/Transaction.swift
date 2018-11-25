//
//  Transaction.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public struct Transaction: BaseModelProtocol {
    let value: Float
    let user: User!
    let card: Card!
    
    enum CodingKeys: String, CodingKey {
        case value
        case user
        case card
    }
    
    public init(value: Float,
                user: User,
                card: Card) {
        self.value = value
        self.user = user
        self.card = card
    }
    
    public init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        value = try values.decodeIfPresent(Float.self, forKey: .value) ?? 0
        user = try values.decodeIfPresent(User.self, forKey: .user)
        card = try values.decodeIfPresent(Card.self, forKey: .card)
    }
    
    public func toDictionary() -> JSON {
        
        let validate = extractDateValidateString(from: card.validate)
        let dict: JSON = [
            "card_number": card.number as Any,
            "cvv": card.cvv as Any,
            "value": value as Any,
            "expiry_date": validate as Any,
            "destination_user_id": user.id as Any
        ]
        
        return dict
    }
    
    fileprivate func extractDateValidateString(from date: Date) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/YY"
        let validate = dateFormatter.string(from: date)

        return validate
    }
}
