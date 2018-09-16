//
//  UserCodable.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 12/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//
// To parse the JSON, add this file to your project and do:
//
//   let users = try? newJSONDecoder().decode(Users.self, from: jsonData)
import Foundation

typealias Users = [User]

struct User: Codable {
    var id: Int = 0
    var name: String = ""
    var img: String = ""
    var username: String = ""
    
    init(id: Int, name: String, img: String, username: String) {
        self.id = id
        self.name = name
        self.img = img
        self.username = username
    }
    
}

