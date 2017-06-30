//
//  UserProfile.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 26/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import RealmSwift
import ObjectMapper

class UserProfile: Object, Mappable {
    dynamic var id: Int = 0
    dynamic var name: String = ""
    dynamic var img: String = ""
    dynamic var username: String = ""
    dynamic var createOn: Date = Date()
    dynamic var updateOn: Date?
    dynamic var selectedToPay: Bool = false
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    override static func primaryKey() -> String? {
        return "id"
    }
    
    override static func indexedProperties() -> [String] {
        return ["username"]
    }
    
    func mapping(map: Map) {
        id              <- map["id"]
        name            <- map["name"]
        img             <- map["img"]
        username        <- map["username"]
    }
}

//class UsersProfile: Mappable {
//
//    var content: [UserProfile]?
//    
//    required convenience init?(map: Map) {
//        self.init()
//    }
//    
//    func mapping(map: Map) {
//        content <- (map, )
//    }
//}
