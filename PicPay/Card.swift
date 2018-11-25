//
//  Card.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public struct Card: BaseModelProtocol {
    let id: Int
    let number: Int
    let validate: Date
    let cvv: Int
    let isActive: Bool
    
    enum CodingKeys: String, CodingKey {
        case id
        case number
        case validate
        case cvv
        case isActive
    }
    
    public init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        id = try values.decodeIfPresent(Int.self, forKey: .id) ?? 0
        number = try values.decodeIfPresent(Int.self, forKey: .number) ?? 0
        validate = try values.decodeIfPresent(Date.self, forKey: .validate) ?? Date()
        cvv = try values.decodeIfPresent(Int.self, forKey: .cvv) ?? 0
        isActive = try values.decodeIfPresent(Bool.self, forKey: .isActive) ?? false
    }
    
    public func toDictionary() -> JSON {
        let dict: JSON = [
            CodingKeys.id.rawValue: id as Any,
            CodingKeys.number.rawValue: number as Any,
            CodingKeys.validate.rawValue: validate as Any,
            CodingKeys.cvv.rawValue: cvv as Any,
            CodingKeys.isActive.rawValue: isActive as Any
        ]
        
        return dict
    }
}
