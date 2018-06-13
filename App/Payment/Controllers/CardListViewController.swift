//
//  CardListViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 10/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

protocol CardListViewControllerDelegate: class {
    func cardListViewController(_ cardListController: CardListViewController, didSelectCard card: Card?)
    func cardListViewControllerDoneButtonTapped(_ cardListController: CardListViewController)
}

private let reuseIdentifier = "cell"

class CardListViewController: UITableViewController {

    var cards = PicPayService.loadCards()
    
    var indexPathForSelectedCard: IndexPath?
    
    weak var delegate: CardListViewControllerDelegate?
    
    @IBOutlet var addButtonItem: UIBarButtonItem!
    @IBOutlet var doneButtonItem: UIBarButtonItem!
    
    @IBAction func done(_ sender: UIBarButtonItem) {
        if let indexPath = indexPathForSelectedCard {
            let userData = NSKeyedArchiver.archivedData(withRootObject: indexPath)
            UserDefaults.standard.set(userData, forKey: reuseIdentifier)
        } else {
            UserDefaults.standard.removeObject(forKey: reuseIdentifier)
        }
        
        delegate?.cardListViewControllerDoneButtonTapped(self)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        if let userData = UserDefaults.standard.object(forKey: reuseIdentifier) as? Data {
            indexPathForSelectedCard = NSKeyedUnarchiver.unarchiveObject(with: userData) as? IndexPath
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        updateNavigationItems(animated)
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        
        if editing {
            navigationItem.setLeftBarButton(addButtonItem, animated: animated)
        } else {
            navigationItem.setLeftBarButton(doneButtonItem, animated: animated)
        }
    }
    
    func updateNavigationItems(_ animated: Bool) {
        if cards.isEmpty {
            navigationItem.setLeftBarButton(doneButtonItem, animated: animated)
            navigationItem.setRightBarButton(addButtonItem, animated: animated)
        } else {
            navigationItem.setLeftBarButton(isEditing ? addButtonItem : doneButtonItem, animated: animated)
            navigationItem.setRightBarButton(editButtonItem, animated: animated)
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
        return tableView.dequeueReusableCell(withIdentifier: reuseIdentifier, for: indexPath)
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        PicPayService.deleteCard(cards.remove(at: indexPath.row))
        tableView.deleteRows(at: [indexPath], with: .automatic)
        updateNavigationItems(true)
        
        if indexPathForSelectedCard == indexPath {
            indexPathForSelectedCard = nil
            delegate?.cardListViewController(self, didSelectCard: nil)
        }
    }
    
    // MARK: - Table view delegate
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        cell.accessoryType = indexPath == indexPathForSelectedCard ? .checkmark : .none
        cell.textLabel?.text = cards[indexPath.row].number
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if isEditing {
            let cardController = storyboard!.instantiateViewController(withIdentifier: "CardViewController") as! CardViewController
            cardController.displayedCard = cards[indexPath.row]
            cardController.delegate = self
            show(cardController, sender: self)
        } else {
            if let oldIndexPath = indexPathForSelectedCard {
                tableView.cellForRow(at: oldIndexPath)?.accessoryType = .none
            }
            indexPathForSelectedCard = indexPath
            tableView.deselectRow(at: indexPath, animated: true)
            tableView.cellForRow(at: indexPath)?.accessoryType = .checkmark
            delegate?.cardListViewController(self, didSelectCard: cards[indexPath.row])
        }
    }
}

extension CardListViewController: CardViewControllerDelegate {
    func cardViewControllerSaveButtonTapped(_ cardController: CardViewController) {
        PicPayService.saveCard(cardController.displayedCard!)
        
        if let indexPath = tableView.indexPathForSelectedRow {
            cards[indexPath.row] = cardController.displayedCard!
            tableView.reloadData()
            
            if indexPath == indexPathForSelectedCard {
                delegate?.cardListViewController(self, didSelectCard: cardController.displayedCard!)
            }
        } else {
            cards.append(cardController.displayedCard!)
            let indexPath = IndexPath(row: cards.count-1, section: 0)
            tableView.insertRows(at: [indexPath], with: .right)
        }
        
        navigationController?.popViewController(animated: true)
    }
}
