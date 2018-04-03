//
//  CreditCardRepository.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 29/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import RealmSwift

class CreditCardRepository : Repository {
    
    func All() -> Results<CreditCard> {
        return realm.objects(CreditCard.self)
    }
}
