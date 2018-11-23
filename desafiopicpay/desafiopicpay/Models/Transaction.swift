//
//  Transaction.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

struct SendTransaction: Codable {
    var cardNumber: String?
    var cvv: Int?
    var value: Float?
    var expiryDate: String?
    var destinationUserId: Int?
}

struct Transaction: Codable {
    var transactionInfo: TransactionInfo?
    
    enum CodingKeys: String, CodingKey {
        case transactionInfo = "transaction"
    }
}

struct TransactionInfo: Codable {
    var id: Int?
    var timestamp: Int?
    var value: Float?
    var destinationUser: User?
    var status: String?
    var success: Bool = false    
}

//{
//    "transaction": {
//        "id": 12314,
//        "timestamp": 1543001513,
//        "value": 79.9,
//        "destination_user": {
//            "id": 1002,
//            "name": "Marina Coelho",
//            "img": "https://randomuser.me/api/portraits/women/37.jpg",
//            "username": "@marina.coelho"
//        },
//        "success": true,
//        "status": "Aprovada"
//    }
//}
