//
//  User.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 28/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation
class User: Codable {
    var id: Int?
    var name: String?
    var imagePath: String?
    var username: String?
    
    enum CodingKeys: String, CodingKey {
        case id
        case name
        case imagePath = "img"
        case username
    }
}
