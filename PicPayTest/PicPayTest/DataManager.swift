//
//  DataManager.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 16/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import Foundation
import CoreData

class DataManager {
    
    // MARK: - Core Data stack
    
    lazy var persistentContainer: NSPersistentContainer = {
        /*
         The persistent container for the application. This implementation
         creates and returns a container, having loaded the store for the
         application to it. This property is optional since there are legitimate
         error conditions that could cause the creation of the store to fail.
         */
        let container = NSPersistentContainer(name: "PicPayTest")
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                
                /*
                 Typical reasons for an error here include:
                 * The parent directory does not exist, cannot be created, or disallows writing.
                 * The persistent store is not accessible, due to permissions or data protection when the device is locked.
                 * The device is out of space.
                 * The store could not be migrated to the current model version.
                 Check the error message to determine what the actual problem was.
                 */
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        return container
    }()
    
    // MARK: - Core Data Saving support
    
    func saveContext () {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }
    
    /**
     Função que salva os genres passados como parametro no coreData
     */
    func saveCartao(withnumero numeroCartao: String,eCvv cvv: String,eVencimento vencimentoCartao: String) {
        
        let managedContext = self.persistentContainer.viewContext
        let entity = NSEntityDescription.entity(forEntityName: "Cartao", in: managedContext)!
        let cartao = NSManagedObject(entity: entity, insertInto: managedContext)
        
        cartao.setValue(numeroCartao, forKey: "numero")
        cartao.setValue(cvv, forKey: "cvv")
        cartao.setValue(vencimentoCartao, forKey: "vencimento")
        
        do {
            try managedContext.save()
        } catch let error as NSError {
            print("Não foi possivel salvar:\(error.localizedDescription)")
        }
    }
    
    func getCartoes() -> [Cartao]? {
        
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Cartao")
        let context = self.persistentContainer.viewContext
        
        do {
            
            let results = try context.fetch(fetchRequest)
            
            let cartoes = results as! [NSManagedObject]
            
            return cartoes as! [Cartao]
            
        } catch let error as NSError {
            print("error: " + error.localizedDescription)
        }
        return nil
    }
    
}
