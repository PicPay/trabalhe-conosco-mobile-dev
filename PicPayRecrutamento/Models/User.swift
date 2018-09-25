//
//  User.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 17/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import Foundation

public class User {
    public let id: Int
    public let name: String
    public let imageURL: URL
    public let username: String
    
    public init(id: Int, name: String, imageURL: URL, username: String) {
        self.id = id
        self.name = name
        self.imageURL = imageURL
        self.username = username
    }
    
}
