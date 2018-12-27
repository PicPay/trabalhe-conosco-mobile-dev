//
//  CredCardCoreData.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 05/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//
import CoreData
import UIKit


class CredCoreData {
    
    private var _credcard: CredCard!
    private var _context: NSManagedObjectContext!
    private let _delegate = UIApplication.shared.delegate as? AppDelegate
    var credcard: CredCard {
        get {
            return self._credcard
        }
    }
    var context: NSManagedObjectContext {
        get {
            return self._context
        }
    }
    
    init() {
        guard let context = self._delegate?.persistentContainer.viewContext else { return }
        self._context = context
        self._credcard = NSEntityDescription.insertNewObject(forEntityName: "CredCard", into: self._context) as? CredCard
    }

    func save() {
        self._credcard.date = Date()
        do {
            try self._context.save()
        } catch let err {
            print(err)
        }
    }
    
    func FetchRequest() -> CredCard? {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "CredCard")
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "date", ascending: false)]
        fetchRequest.fetchLimit = 1
        do {
            let fetch = try self._context.fetch(fetchRequest) as! [CredCard]
            return fetch.last!
        } catch let err {
            print(err)
        }
        return CredCard()
    }
}
