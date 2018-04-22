//
//  User.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

struct User {
    var id: Int
    var name: String
    var img: URL?
    var username: String
}

extension User: Decodable {
    
    enum UserCodingKeys: String, CodingKey {
        case id
        case name
        case img
        case username
    }
    
    init(from decoder: Decoder) throws {
        let movieContainer = try decoder.container(keyedBy: UserCodingKeys.self)
        id = try movieContainer.decode(Int.self, forKey: .id)
        name = try movieContainer.decode(String.self, forKey: .name)
        img = try? movieContainer.decode(URL.self, forKey: .img)
        username = try movieContainer.decode(String.self, forKey: .username)
    }
}
