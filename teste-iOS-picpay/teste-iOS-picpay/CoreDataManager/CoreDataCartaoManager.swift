//
//  CoreDataCartaoManager.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class CoreDataCartaoManager {
    
    /// Basic configuration for the core date works
    ///
    /// - Returns: returns the Core Data
    private class func getContext() -> NSManagedObjectContext {
        let appDelegate: AppDelegate = UIApplication.shared.delegate as! AppDelegate
        return appDelegate.persistentContainer.viewContext
    }
    
    
    /// Estrutura para retornar o cartao persistido no CoreData
    ///
    /// - Returns: returns an array of User
    class func fetchCartao() -> Cartao? {
        let context = getContext()
        var user: [Cartao]? = nil
        
        do {
            user = try context.fetch(Cartao.fetchRequest())
            return user?.first
        } catch let error {
            print(error.localizedDescription)
            return nil
        }
    }
    
    
    /// Extrutura basica para persistir o Cartao no Core Data
    ///
    /// - Parameters:
    ///   - numero: numero do cartao
    ///   - cvv: codigo de segurança do cartão
    ///   - expiryDate: data de validade
    /// - Returns: true ou false, dependendo de se o context conseguiu persistir
    class func salvaCartao(numero: String, cvv: Int, expiryDate: String) -> Bool {
        let context = CoreDataCartaoManager.getContext()
        let entity = NSEntityDescription.entity(forEntityName: "Cartao", in: context)
        let manageObject = NSManagedObject(entity: entity!, insertInto: context)
        manageObject.setValue(numero, forKey: "numero")
        manageObject.setValue(cvv, forKey: "cvv")
        manageObject.setValue(expiryDate, forKey: "expiryDate")
        
        do {
            try context.save()
            return true
        } catch let error {
            print(error.localizedDescription)
            return false
        }
    }
    
    /// Extrutura para eliminar o Cartao
    ///
    /// - Parameter cartao: Cartao a ser deletado
    /// - Returns: true or false depending what happens
    class func deleteObject(cartao: Cartao) -> Bool {
        let context = getContext()
        context.delete(cartao)
        
        do {
            try context.save()
            return true
        } catch let error {
            print(error.localizedDescription)
            return false
        }
    }
    
    /// Deleta todos os cartoes do coredata
    ///
    /// - Returns: true or false depending what happens
    class func cleanDelete() -> Bool {
        let context = getContext()
        let delete = NSBatchDeleteRequest(fetchRequest: Cartao.fetchRequest())
        
        do {
            try context.execute(delete)
            return true
        } catch let error {
            print(error.localizedDescription)
            return false
        }
    }
    
    /// Estrutura para filtrar um cartao dentro de um array de Cartao
    ///
    /// - Parameter numero: numero a ser filtrado
    /// - Returns: retorna uma estrutura de cartao contendo apenas um ou mais cartoes
    class func filterData(numero: String) -> [Cartao]? {
        let context = getContext()
        let fetchRequest: NSFetchRequest<Cartao> = Cartao.fetchRequest()
        var user: [Cartao]? = nil
        
        let predicate = NSPredicate(format: "numero contains[c] %@", numero)
        fetchRequest.predicate = predicate
        
        do {
            user = try context.fetch(fetchRequest)
            return user
        } catch let error {
            print(error.localizedDescription)
            return user
        }
    }

}
