//
//  AccountStore.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class AccountStorage {
    
    var account: Account!
    let defaults = UserDefaults.standard
    fileprivate let accountKey = "account.archive"

    init() {
        account = Account(id: 1, name: "User Test", cards: [])
        if let data = defaults.object(forKey: accountKey) as? Data {
            let decoder = JSONDecoder()
            if let archivedAccount = try? decoder.decode(Account.self, from: data) {
                account = archivedAccount
            }
        }
    }
    
    @discardableResult
    func saveChanges() -> Bool {
        let encoder = JSONEncoder()
        if let encoded = try? encoder.encode(account) {
            defaults.set(encoded, forKey: accountKey)
            
            return true
        } else {
            return false
        }
    }

}
