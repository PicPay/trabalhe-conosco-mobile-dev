//
//  Person.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 08/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

struct Person: Decodable {
    let id: Int
    let name: String
    let photoURL: URL
    let username: String
    
    enum CodingKeys: String, CodingKey {
        case id
        case name
        case photoURL = "img"
        case username
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        let id = try container.decode(Int.self, forKey: .id)
        
        guard id >= 0 && id <= Int32.max else {
            throw DecodingError.dataCorruptedError(forKey: .id, in: container,
                                                   debugDescription: "id out of bounds")
        }
        
        self.id = id
        self.name = try container.decode(String.self, forKey: .name)
        self.photoURL = try container.decode(URL.self, forKey: .photoURL)
        self.username = try container.decode(String.self, forKey: .username)
    }
}
