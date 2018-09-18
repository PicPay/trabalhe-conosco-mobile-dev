//
//  CoreDataService.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/18/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import CoreData
import UIKit

class CoreDataService {
    
    static let sharedInstance = CoreDataService()
    
    let mainContext: NSManagedObjectContext? = {
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return nil
        }
        
        return appDelegate.persistentContainer.viewContext
    }()
    
    @discardableResult func addNewEntity(entityName: String, info: [String: Any]) -> NSManagedObject? {
        guard let context = self.mainContext, let entity = NSEntityDescription.entity(forEntityName: entityName, in: context) else {
            return nil
        }

        let object = NSManagedObject(entity: entity, insertInto: context)
        
        for (key, value) in info {
            object.setValue(value, forKey: key)
        }
        
        do {
            try context.save()
            return object
        } catch {
            return nil
        }
    }
    
    func fetch(entityName: String) -> [NSManagedObject]? {
        guard let context = self.mainContext else {
            return nil
        }
        
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: entityName)
        
        do {
            return try context.fetch(fetchRequest)
        } catch {
            return nil
        }
    }
    
    
    
    
}
