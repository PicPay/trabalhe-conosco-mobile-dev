//
//  Repository.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 26/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import RealmSwift

class Repository {
    
    let realm = try! Realm()
    
    func Add(item: Object) {
        try! realm.write {
            realm.add(item)
        }
    }
    
    func Update(item: Object) {
        try! realm.write {
            realm.add(item, update: true)
        }
    }
    
    func Delete(item: Object) {
        try! realm.write {
            realm.delete(item)
        }
    }
    
    func Get<T>(query: NSPredicate, sortedBy: String, asc: Bool) -> Results<T> {
        return realm.objects(T.self).filter(query).sorted(byKeyPath: sortedBy, ascending: asc)
    }
}
