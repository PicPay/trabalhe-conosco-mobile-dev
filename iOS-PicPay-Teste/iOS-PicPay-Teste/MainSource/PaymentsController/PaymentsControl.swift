//
//  PaymentsControl.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 06/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import CoreData
import UIKit


class PaymentsCoreData {
    
    private var _payment: Payment!
    private var _user: User!
    private var _context: NSManagedObjectContext!
    private let _delegate = UIApplication.shared.delegate as? AppDelegate
    
    var payment: Payment {
        get {
            return self._payment
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
        self._payment = NSEntityDescription.insertNewObject(forEntityName: "Payment", into: self._context) as? Payment
        self._payment = NSEntityDescription.insertNewObject(forEntityName: "Payment", into: self._context) as? Payment
    }
    
    func save() {
        do {
            try self._context.save()
        } catch let err {
            print(err)
        }
    }
    
    func loadData() -> Payment {
    
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Payment")
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "date", ascending: false)]
        fetchRequest.fetchLimit = 1
        do {
            let fetch = try self._context.fetch(fetchRequest) as! [Payment]
            return fetch.last!
        } catch let err {
            print(err)
        }
        return Payment()
    }
}
