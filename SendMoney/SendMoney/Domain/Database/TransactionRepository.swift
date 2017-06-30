//
//  TransactionRepository.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 30/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import RealmSwift

class TransactionRepository : Repository {
    func All() -> Results<Transaction> {
        return realm.objects(Transaction.self)
    }
    
    func incrementId() -> Int {
        let realm = try! Realm()
        return (realm.objects(Transaction.self).max(ofProperty: "id") as Int? ?? 0) + 1
    }
}
