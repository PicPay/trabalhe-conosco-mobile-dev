//
//  CardViewModel.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 20/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import Foundation

public class CardViewModel {
    private let card: Card?
    
    public init(card: Card?) {
        self.card = card
    }
    
    public var name: String? {
        return card?.card_name
    }
    
    public var card_number: String {
        return card!.card_number
    }
    
    public var cvv: Int {
        return card!.cvv
    }
    
    public var expiry_date: String {
        return card!.expiry_date
    }
    
    public var isMainCard : Bool {
        return card!.isMainCard
    }
}
