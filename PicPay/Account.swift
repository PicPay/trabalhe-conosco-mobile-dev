//
//  Account.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public struct Account: BaseModelProtocol {
    let id: Int
    let name: String
    var cards: [Card]
    var activeCard: Card? {
        let card = cards.filter ({ $0.isMainCard }).first
        return card
    }
    var isCardsEmpty: Bool? {
        let qtCards = cards.count
        
        return qtCards == 0
    }
    
    enum CodingKeys: String, CodingKey {
        case id
        case name
        case cards
    }
    
    public init(id: Int,
                name: String,
                cards: [Card]) {
        self.id = id
        self.name = name
        self.cards = cards
    }
    
    public init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        id = try values.decodeIfPresent(Int.self, forKey: .id) ?? 0
        name = try values.decodeIfPresent(String.self, forKey: .name) ?? "Não informado"
        cards = try values.decodeIfPresent([Card].self, forKey: .cards) ?? []
    }
    
    public func toDictionary() -> JSON {
        let dict: JSON = [
            CodingKeys.id.rawValue: id as Any,
            CodingKeys.name.rawValue: id as Any,
            CodingKeys.cards.rawValue: id as Any
        ]
        
        return dict
    }
}
