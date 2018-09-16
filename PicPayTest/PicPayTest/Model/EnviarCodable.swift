//
//  EnviarCodable.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 16/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

// To parse the JSON, add this file to your project and do:
//
//   let transaction = try? newJSONDecoder().decode(Transaction.self, from: jsonData)
import Foundation

struct Transaction: Codable {
    let transaction: TransactionClass?
}

struct TransactionClass: Codable {
    let id, timestamp, value: Int?
    let destinationUser: DestinationUser?
    let success: Bool?
    let status: String?
    
    enum CodingKeys: String, CodingKey {
        case id, timestamp, value
        case destinationUser = "destination_user"
        case success, status
    }
}

struct DestinationUser: Codable {
    let id: Int?
    let name: String?
    let img: String?
    let username: String?
}

