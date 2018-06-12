//
//  MainViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 08/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class MainViewController: UITableViewController {

    var selectedPerson: Person?
    
    var selectedCard: Card?
    
    // MARK: - Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let navigationController = segue.destination as? UINavigationController,
                let rootViewController = navigationController.viewControllers.first else {
            return
        }
        
        if let pickerController = rootViewController as? PersonPickerController {
            pickerController.delegate = self
        } else if let cardListController = rootViewController as? CardListViewController {
            cardListController.delegate = self
        }
    }
    
    // MARK: UITableViewDelegate
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        switch indexPath.section {
        case 0:
            if let person = selectedPerson {
                cell.textLabel?.text = person.name
                cell.textLabel?.textColor = .darkText
                cell.detailTextLabel?.text = person.username
                cell.detailTextLabel?.textColor = .darkText
            } else {
                cell.textLabel?.text = "Pessoa"
                cell.textLabel?.textColor = .lightGray
                cell.detailTextLabel?.text = nil
            }
        case 1:
            if let card = selectedCard {
                cell.textLabel?.text = card.cardNumber
                cell.textLabel?.textColor = .darkText
            } else {
                cell.textLabel?.text = "Cartão"
                cell.textLabel?.textColor = .lightGray
            }
        default:
            break
        }
    }
}

extension MainViewController: PersonPickerControllerDelegate {
    func personPickerController(_ pickerController: PersonPickerController, didPickPerson person: Person) {
        selectedPerson = person
        tableView.reloadData()
        pickerController.dismiss(animated: true)
    }
    
    func personPickerControllerDidCancel(_ pickerController: PersonPickerController) {
        tableView.deselectRow(at: IndexPath(row: 0, section: 0), animated: true)
        pickerController.dismiss(animated: true)
    }
}

extension MainViewController: CardListViewControllerDelegate {
    func cardListViewController(_ cardListController: CardListViewController, didSelectCard card: Card) {
        selectedCard = card
        tableView.reloadData()
    }
    
    func cardListViewControllerDone(_ cardListController: CardListViewController) {
        tableView.deselectRow(at: IndexPath(row: 0, section: 1), animated: true)
        cardListController.dismiss(animated: true)
    }
}
