//
//  Card.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import RealmSwift

class Card: Object {
    @objc dynamic var pan = ""
    @objc dynamic var cvv = ""
    @objc dynamic var expiryDate = ""
    
    override static func primaryKey() -> String? {
        return "pan"
    }
    
    static func findCards() -> [Card]? {
        let realm = try! Realm()
        let result = realm.objects(Card.self)
        
        var cards = [Card]()
        for card in result {
            cards.append(card)
        }
        
        return cards
    }
    
    static func persist(card: Card!) {
        let realm = try! Realm()
        try! realm.write {
            realm.add(card)
            configureBackup()
        }
    }
    
    static func configureBackup() {
        let realm = try! Realm()
        var config = realm.configuration
        do {
            var resourceValues = URLResourceValues()
            resourceValues.isExcludedFromBackup = true
            try config.fileURL?.setResourceValues(resourceValues)
        } catch {
            debugPrint("Failed to set resource value")
        }
    }
    
    static func clear() {
        let realm = try! Realm()
        let cards = realm.objects(Card.self)
        if cards.count > 0 {
            try! realm.write {
                realm.delete(cards)
            }
        }
    }
    
    static func delete(card: Card) {
        let realm = try! Realm()
        try! realm.write {
            realm.delete(card)
        }
    }
    
    static func save(card : Card?) {
        guard card != nil else { return }
        self.persist(card: card)
    }
}


