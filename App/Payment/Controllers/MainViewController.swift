//
//  MainViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 08/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class MainViewController: UITableViewController {

    var card: Card?
    
    var person: Person?
    
    @IBOutlet var button: UIButton! {
        didSet {
            button.titleLabel?.adjustsFontForContentSizeCategory = true
        }
    }
    
    // MARK: - Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let navigationController = segue.destination as? UINavigationController,
                let rootViewController = navigationController.viewControllers.first else {
            return
        }
        
        if let pickerController = rootViewController as? PersonPickerController {
            pickerController.delegate = self
        } else if let cardListController = rootViewController as? CardListViewController {
            if card == nil { cardListController.indexPathForSelectedCard = nil }
            cardListController.delegate = self
        } else if let paymentController = rootViewController as? PaymentViewController {
            paymentController.paymentInfo = (person!, card!)
        }
    }
    
    // MARK: UITableViewDelegate
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return indexPath.section == 0 ? 64.0 : UITableViewAutomaticDimension
    }
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        switch indexPath.section {
        case 0:
            if let p = person {
                cell.textLabel?.text = p.name
                cell.textLabel?.textColor = .darkText
                cell.detailTextLabel?.text = p.username
                cell.detailTextLabel?.textColor = .darkText
                cell.imageView?.image = UIImage(named: "Placeholder")
                
                DispatchQueue.global(qos: .background).async {
                    let image: UIImage?
                    
                    do {
                        image = UIImage(data: try Data(contentsOf: p.photoURL))
                    } catch {
                        image = UIImage(named: "Placeholder")
                    }
                    
                    DispatchQueue.main.async {
                        cell.imageView?.image = image
                    }
                }
                
                cell.imageView?.layer.cornerRadius = 32.0
                cell.imageView?.layer.masksToBounds = true
            } else {
                cell.textLabel?.text = nil
                cell.detailTextLabel?.text = "Pessoa"
                cell.detailTextLabel?.textColor = .lightGray
                cell.imageView?.image = nil
            }
        case 1:
            if let c = card {
                cell.textLabel?.text = "\(c.description) (\(String(repeating: "•••• ", count: 3))\(c.number.suffix(4)))"
                cell.textLabel?.textColor = .darkText
            } else {
                cell.textLabel?.text = "Cartão"
                cell.textLabel?.textColor = .lightGray
            }
        case 2:
            button.isEnabled = person != nil && card != nil
        default:
            break
        }
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension MainViewController: PersonPickerControllerDelegate {
    func personPickerController(_ pickerController: PersonPickerController, didPickPerson person: Person) {
        self.person = person
        tableView.reloadData()
        pickerController.dismiss(animated: true)
    }
    
    func personPickerControllerDidCancel(_ pickerController: PersonPickerController) {
        pickerController.dismiss(animated: true)
    }
}

extension MainViewController: CardListViewControllerDelegate {
    func cardListViewController(_ cardListController: CardListViewController, didSelectCard card: Card?) {
        self.card = card
        tableView.reloadSections(IndexSet(integersIn: 1...2), with: .none)
    }
    
    func cardListViewControllerDoneButtonTapped(_ cardListController: CardListViewController) {
        cardListController.dismiss(animated: true)
    }
}
