//
//  User.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation


struct Users: Codable {
    var users: [User]?
}

struct User: Codable {
    var id: Int?
    var name: String?
    var img: String?
    var username: String?
}
