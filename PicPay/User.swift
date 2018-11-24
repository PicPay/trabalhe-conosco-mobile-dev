//
//  User.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public struct User: BaseModelProtocol {
    let id: Int
    let name: String
    let img: String
    let username: String
    
    enum CodingKeys: String, CodingKey {
        case id
        case name
        case img
        case username
    }
    
    public init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        id = try values.decodeIfPresent(Int.self, forKey: .id) ?? 0
        name = try values.decodeIfPresent(String.self, forKey: .name) ?? "Não informado"
        img = try values.decodeIfPresent(String.self, forKey: .img) ?? "https://via.placeholder.com/128"
        username = try values.decodeIfPresent(String.self, forKey: .username) ?? "Não informado"
    }

    public func toDictionary() -> JSON {
        let dict: JSON = [
            CodingKeys.id.rawValue: id as Any,
            CodingKeys.name.rawValue: id as Any,
            CodingKeys.img.rawValue: id as Any,
            CodingKeys.username.rawValue: id as Any
        ]

        return dict
    }
}
