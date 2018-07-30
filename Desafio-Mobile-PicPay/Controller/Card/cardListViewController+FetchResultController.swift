//
//  cardListViewController+FetchResultController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 26/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit
import CoreData

extension CardListViewController{
    
    func setUpFetchResultController(){
        fetchedResultsController = appDelegate.coreDataManager.fetchCards()
        fetchedResultsController.delegate = self
        do {
            try fetchedResultsController.performFetch()
        } catch {
            fatalError("Fetch Error: \(error.localizedDescription)")
        }
    }
    
    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange anObject: Any, at indexPath: IndexPath?, for type: NSFetchedResultsChangeType, newIndexPath: IndexPath?) {
        
        switch type {
        case .insert:
            guard let newIndexPath = newIndexPath else {return}
            self.tableView.insertRows(at: [newIndexPath], with: .fade)           
            break
        case .delete:
            guard let indexPath = indexPath else {return}
            self.tableView.deleteRows(at: [indexPath], with: .fade)
            break
        case .update:
            guard let indexPath = indexPath else {return}
            self.tableView.reloadRows(at: [indexPath], with: .fade)
            break
        case .move:
            guard let newIndexPath = newIndexPath else {return}
            guard let indexPath = indexPath else {return}
            self.tableView.moveRow(at: indexPath, to: newIndexPath)
        }
    }
    
    func controllerWillChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        tableView.beginUpdates()
    }
    
    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        tableView.endUpdates()
    }
    
}
