//
//  CoreDataManager.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import Foundation
import CoreData

class CoreDataManager {
    
    let persistentContainer: NSPersistentContainer = {
        let container = NSPersistentContainer(name: "DatabaseModel")
        container.loadPersistentStores { (_, error) in
            if let error = error {
                fatalError("Store failed: \(error)")
            }
        }
        return container
    }()
    
    func fetchCards() -> NSFetchedResultsController<Card> {
        let fetchRequest = NSFetchRequest<Card>(entityName: "Card")
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "name", ascending: false)]
        let fetchedResultController = NSFetchedResultsController(fetchRequest: fetchRequest, managedObjectContext: persistentContainer.viewContext, sectionNameKeyPath: nil, cacheName: "Card")
        return fetchedResultController
    }
    
    func fetchTransactions() -> [Transaction] {
        let fetchRequest = NSFetchRequest<Transaction>(entityName: "Transaction")
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "date", ascending: false)]
        guard let transactions = try? persistentContainer.viewContext.fetch(fetchRequest) else {return []}
        
        return transactions
    }
    
    func fetchSelectedCard() -> Card? {
        guard let selectedCardUrl = UserDefaults.standard.object(forKey: "selectedCardID") as? String else {return nil}
        let fetchRequest = NSFetchRequest<Card>(entityName: "Card")
        var selectedCard: Card?
        guard let result = try? persistentContainer.viewContext.fetch(fetchRequest) else {return nil}
        
        if(result.count == 1){
            return result.first
        }
        
        let _ = result.filter { (card) -> Bool in
            if (card.url_string_id! == selectedCardUrl){
                selectedCard = card
                return true
            }
            return false
        }
        return selectedCard
        
    }
    
    func saveCard(_ cardName: String, cardNumber: String, cvv: String, date: String){
        let card = Card(context: persistentContainer.viewContext)
        card.name = cardName
        card.card_number = cardNumber.replacingOccurrences(of: " ", with: "")
        card.cvv = cvv
        card.expiry_date = date
        card.url_string_id = card.objectID.uriRepresentation().absoluteString
        self.save()
        if(UserDefaults.standard.object(forKey: "selectedCardID") == nil){
            UserDefaults.standard.set(card.url_string_id, forKey: "selectedCardID")
            NotificationCenter.default.post(name: PaymentViewController.updatePaymentTypeNotificationName, object: self)
            UserDefaults.standard.set(0, forKey: "selectedCardIndex")
        }
    }
    
    func saveTransaction(_ jsonTransactionApiObject: JsonTransactionApiObject){
        let transactionUser = self.saveTransactionUser(jsonTransactionApiObject.transaction.destination_user)
        let transaction = Transaction(context: persistentContainer.viewContext)
        transaction.transaction_user = transactionUser
        transaction.date = Date(timeIntervalSince1970: TimeInterval(jsonTransactionApiObject.transaction.timestamp))
        transaction.status = jsonTransactionApiObject.transaction.status
        transaction.success = jsonTransactionApiObject.transaction.success
        transaction.transaction_id = Int64(jsonTransactionApiObject.transaction.id)
        transaction.value = jsonTransactionApiObject.transaction.value
        self.save()
    }
    
    func saveTransactionUser(_ user: Contact)->TransactionUser{
        let transactionUser = TransactionUser(context: persistentContainer.viewContext)
        transactionUser.user_id = Int64(user.id)
        transactionUser.username = user.username
        transactionUser.profile_image_url = user .img
        self.save()
        return transactionUser
    }
    
    func save(){
        if(persistentContainer.viewContext.hasChanges){
            try? persistentContainer.viewContext.save()
        }
    }
}
