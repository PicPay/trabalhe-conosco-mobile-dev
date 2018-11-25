//
//  Card.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public struct Card: BaseModelProtocol {
    let number: Int
    let validate: String
    let cvv: Int
    let isMainCard: Bool
    
    enum CodingKeys: String, CodingKey {
        case number
        case validate
        case cvv
        case isMainCard
    }
    
    public init(number: Int, validate: String, cvv: Int, isMainCard: Bool) {
        self.number = number
        self.validate = validate
        self.cvv = cvv
        self.isMainCard = isMainCard
    }
    
    public init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        number = try values.decodeIfPresent(Int.self, forKey: .number) ?? 0
        validate = try values.decodeIfPresent(String.self, forKey: .validate) ?? ""
        cvv = try values.decodeIfPresent(Int.self, forKey: .cvv) ?? 0
        isMainCard = try values.decodeIfPresent(Bool.self, forKey: .isMainCard) ?? false
    }
    
    public func toDictionary() -> JSON {
        let dict: JSON = [
            CodingKeys.number.rawValue: number as Any,
            CodingKeys.validate.rawValue: validate as Any,
            CodingKeys.cvv.rawValue: cvv as Any,
            CodingKeys.isMainCard.rawValue: isMainCard as Any
        ]
        
        return dict
    }
}
