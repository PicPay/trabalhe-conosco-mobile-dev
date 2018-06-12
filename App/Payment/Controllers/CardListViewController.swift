//
//  CardListViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 10/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

protocol CardListViewControllerDelegate: class {
    func cardListViewController(_ cardListController: CardListViewController, didSelectCard card: Card)
    func cardListViewControllerDone(_ cardListController: CardListViewController)
}

private let reuseIdentifier = "cell"

class CardListViewController: UITableViewController {

    fileprivate var cards = [Card]() {
        didSet {
            if cards.isEmpty {
                navigationItem.rightBarButtonItem = addButtonItem
            } else {
                navigationItem.rightBarButtonItem = editButtonItem
            }
        }
    }
    
    var indexPathForSelectedCard: IndexPath? {
        willSet {
            if let indexPath = indexPathForSelectedCard, isViewLoaded {
                tableView.cellForRow(at: indexPath)?.accessoryType = .none
            }
        }
        didSet {
            if let indexPath = indexPathForSelectedCard {
                delegate?.cardListViewController(self, didSelectCard: cards[indexPath.row])
            }
        }
    }
    
    weak var delegate: CardListViewControllerDelegate?
    
    @IBOutlet var addButtonItem: UIBarButtonItem!
    @IBOutlet var doneButtonItem: UIBarButtonItem!
    
    @IBAction func done(_ sender: UIBarButtonItem) {
        delegate?.cardListViewControllerDone(self)
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        
        if editing {
            navigationItem.setLeftBarButton(addButtonItem, animated: animated)
        } else {
            navigationItem.setLeftBarButton(doneButtonItem, animated: animated)
        }
    }
    
    // MARK: - Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let cardController = segue.destination as? CardViewController {
            cardController.delegate = self
        }
    }
    
    // MARK: - Table view data source
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cards.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier, for: indexPath)
        
        cell.textLabel?.text = cards[indexPath.row].cardNumber

        return cell
    }
    
    // MARK: - Table view delegate
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if isEditing {
            let cardController = storyboard!.instantiateViewController(withIdentifier: "CardViewController") as! CardViewController
            cardController.displayedCard = cards[indexPath.row]
            cardController.delegate = self
            show(cardController, sender: self)
        } else {
            indexPathForSelectedCard = indexPath
            tableView.deselectRow(at: indexPath, animated: true)
            tableView.cellForRow(at: indexPath)?.accessoryType = .checkmark
        }
    }
    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */
}

extension CardListViewController: CardViewControllerDelegate {
    func cardViewControllerDidUpdateDisplayedCard(_ cardController: CardViewController) {
        if let indexPath = tableView.indexPathForSelectedRow {
            cards[indexPath.row] = cardController.displayedCard!
            tableView.reloadData()
        } else {
            cards.append(cardController.displayedCard!)
            let indexPath = IndexPath(row: cards.count-1, section: 0)
            tableView.insertRows(at: [indexPath], with: .right)
        }
        
        navigationController?.popViewController(animated: true)
    }
}
