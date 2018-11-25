//
//  CredcardManager.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class CredcardManager: NSObject {
    fileprivate let business: CredcardBusiness = CredcardBusiness()
    
    public func register(card: Card, with accountStore: AccountStore, completion: @escaping (DAOResult) -> Void) {
        OperationQueue.main.addOperation { [weak self] in
            self?.business.register(card: card, with: accountStore, completion: completion)
        }
    }
    
    public func setAsMainCard(card: Card, with accountStore: AccountStore) {
        OperationQueue.main.addOperation { [weak self] in
            self?.business.setAsMainCard(card: card, with: accountStore)
        }
    }
}
