//
//  TicketDataSource.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 22/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit
import CoreData

class TicketDataSource {
    
    private var _friend: Friend!
    private var _context: NSManagedObjectContext!
    private let _delegate = UIApplication.shared.delegate as? AppDelegate
    var friend: Friend {
        get {
            return self._friend
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
        self._friend = NSEntityDescription.insertNewObject(forEntityName: "Friend", into: self._context) as? Friend
    }
    
    
    
    func save() {
        do {
            try self._context.save()
        } catch let err {
            print(err)
        }
    }
    
    
    
    func FetchRequest(with name: String) -> [Ticket]? {
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Ticket")
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "timestamp", ascending: false)]
        fetchRequest.predicate = NSPredicate(format: "friend.username = %@", name)
        do {
            let fetch = try self._context.fetch(fetchRequest) as! [Ticket]
            return fetch
        } catch let err {
            print(err)
        }
        return nil
    }
    
    
    func createTicketForFriend(use ticketU: TicketUser) -> Ticket {
        self._friend.nome = ticketU.transaction.destination_user.name
        self._friend.id = NSDecimalNumber(integerLiteral: ticketU.transaction.destination_user.id)
        self._friend.img = ticketU.transaction.destination_user.img
        self._friend.username = ticketU.transaction.destination_user.username
        
        let ticket = NSEntityDescription.insertNewObject(forEntityName: "Ticket", into: self._context) as! Ticket
        ticket.friend = self._friend
        ticket.id = Int64(ticketU.transaction.id)
        ticket.status = ticketU.transaction.status
        ticket.success = ticketU.transaction.success
        ticket.timestamp = Date(timeIntervalSince1970: TimeInterval(ticketU.transaction.timestamp))
        ticket.value = ticketU.transaction.value
        return ticket
    }
}
