//
//  AccountStore.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class AccountStore {
    
    var account: Account!
    let accountArchiveURL: URL = {
        let documentsDirectories = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let documentDirectory = documentsDirectories.first!
        return documentDirectory.appendingPathComponent("account.archive")
    }()
    
    init() {
        let archivedAccount = NSKeyedUnarchiver.unarchiveObject(withFile: self.accountArchiveURL.path) as? Account
        let newAccount = Account(id: 1, name: "User Test", cards: [])
        
        account = archivedAccount ?? newAccount
    }
    
    func saveChanges() -> Bool {
        return NSKeyedArchiver.archiveRootObject(account, toFile: self.accountArchiveURL.path)
    }

}
