//
//  TicketModel.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 07/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import Foundation

struct TicketUser: Decodable {
    let transaction: transaction
}

struct transaction: Decodable {
    let destination_user: Contato
    let id: Int
    let status: String
    let success: Bool
    let timestamp: Int
    let value: Double
}
