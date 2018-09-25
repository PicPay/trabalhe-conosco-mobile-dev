//
//  Transaction.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 18/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import Foundation

//struct structTransaction: Codable {
//    let transaction : Transaction?
//}
public class Transaction {
    
    // referente a transação
    public let card_number : String
    public let cvv: Int
    public var value: Double
    public let expiry_date : String
    public let destination_user: Int
    
    
    public init(card_number: String, cvv: Int, value: Double, expiry_date: String, destination_user: Int) {
        self.card_number = card_number
        self.value = value
        self.destination_user = destination_user
        self.expiry_date = expiry_date
        self.cvv = cvv
    }
    
//    private enum CodingKeys : String, CodingKey {
//        case card_number
//        case cvv
//        case value
//        case expiry_date
//        case destination_user
//    }
    
}
