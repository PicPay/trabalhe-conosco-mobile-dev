//
//  Transaction.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

struct Transaction {
    var id: Int
    var timestamp: Date
    var value: Double
    var destinationUser: User
    var success: Bool
    var status: String
}

extension Transaction: Decodable {
    
    enum TransactionCodingKeys: String, CodingKey {
        case id
        case timestamp
        case value
        case destinationUser = "destination_user"
        case success
        case status
    }
    
    init(from decoder: Decoder) throws {
        let movieContainer = try decoder.container(keyedBy: TransactionCodingKeys.self)
        id = try movieContainer.decode(Int.self, forKey: .id)
        timestamp = try movieContainer.decode(Date.self, forKey: .timestamp)
        value = try movieContainer.decode(Double.self, forKey: .value)
        destinationUser = try movieContainer.decode(User.self, forKey: .destinationUser)
        success = try movieContainer.decode(Bool.self, forKey: .success)
        status = try movieContainer.decode(String.self, forKey: .status)
    }
}
