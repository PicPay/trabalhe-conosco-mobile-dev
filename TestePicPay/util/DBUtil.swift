//
//  DBUtil.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 30/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation
import SQLite

class DBUtil {
    
    static let shared = DBUtil()
    
    var database: Connection!
    
    //Credit Cards:
    let cardsTable = Table("cards")
    let id = Expression<Int>("id")
    let brand = Expression<String>("brand")
    let name = Expression<String>("name")
    let number = Expression<String>("number")
    let expire = Expression<Date>("expire")
    let cvc = Expression<String>("cvc")
    let cep = Expression<String>("cep")
    
    private init(){
        do{
            let documentDirectory = try FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
            let fileUrl = documentDirectory.appendingPathComponent("cards").appendingPathExtension("sqlite3")
            let database = try Connection(fileUrl.path)
            self.database = database
        } catch {
            print(error)
        }
    }
    
    func createCardsTableIfNotExists(){
        let createTableTask = cardsTable.create(ifNotExists: true) { (table) in
            table.column(id, primaryKey: true)
            table.column(brand)
            table.column(name)
            table.column(number, unique: true)
            table.column(expire)
            table.column(cvc)
            table.column(cep)
        }
        
        do{
            try self.database.run(createTableTask)
            print("Table created")
        } catch {
            print(error)
        }
    }
    
    func addCard(_ card: CreditCard){
        createCardsTableIfNotExists()
        let addCardTask = cardsTable.insert(
            brand <- (card.brand?.rawValue)!,
            name <- card.name!,
            number <- card.number!,
            expire <- card.expireDate!,
            cvc <- card.cvc!,
            cep <- card.cep!)
        
        do{
            try database.run(addCardTask)
            print("Inserted Card")
        } catch {
            print(error)
        }
    }
    
    func listCards(){
        do{
            let cards = try database.prepare(cardsTable)
            for card in cards {
                print("""
                    id: \(card[self.id]),
                    brand: \(card[self.brand]),
                    name: \(card[self.name]),
                    number: \(card[self.number]),
                    expire: \(card[self.expire]),
                    cvc: \(card[self.cvc]),
                    cep: \(card[self.id])
                    """)
            }
        } catch {
            print(error)
        }
    }
}
