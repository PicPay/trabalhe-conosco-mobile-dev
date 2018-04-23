//
//  TransactionResponse.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

struct TransactionResponse {
    var transaction: Transaction
}

extension TransactionResponse: Decodable {
    
    enum TransactionResponseCodingKeys: String, CodingKey {
        case transaction
    }
    
    init(from decoder: Decoder) throws {
        let movieContainer = try decoder.container(keyedBy: TransactionResponseCodingKeys.self)
        transaction = try movieContainer.decode(Transaction.self, forKey: .transaction)
    }
}
