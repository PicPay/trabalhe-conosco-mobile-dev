//
//  CardViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 11/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

protocol CardViewControllerDelegate: class {
    func cardViewControllerSaveButtonTapped(_ cardController: CardViewController)
}

class CardViewController: UITableViewController {

    var isValidCard: Bool {
        guard let number = cardNumberField.text, Card.isValidNumber(number) else {
            return false
        }
        
        guard let date = datePickerButton.titleLabel?.text, Card.isValidDate(date) else {
            return false
        }
        
        guard let code = securityCodeField.text, Card.isValidSecurityCode(code) else {
            return false
        }
        
        guard let description = descriptionField.text, Card.isValidDescription(description) else {
            return false
        }
        
        return true
    }
    
    var displayedCard: Card?
    
    weak var delegate: CardViewControllerDelegate?
    
    @IBOutlet var cardNumberField: UITextField!
    @IBOutlet var datePickerButton: DatePickerButton!
    @IBOutlet var securityCodeField: UITextField!
    @IBOutlet var saveButtonItem: UIBarButtonItem!
    @IBOutlet var descriptionField: UITextField!
    
    @IBAction func edit(_ sender: UITextField) {
        saveButtonItem.isEnabled = isValidCard
    }
    
    @IBAction func save(_ sender: UIBarButtonItem) {
        
        displayedCard = Card(
            id: displayedCard?.id ?? NSUUID().uuidString,
            number: cardNumberField.text!,
            expires: datePickerButton.titleLabel!.text!,
            description: descriptionField.text!,
            securityCode: securityCodeField.text!)
        
        delegate?.cardViewControllerSaveButtonTapped(self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let card = displayedCard {
            cardNumberField.text = card.number
            descriptionField.text = card.description
            datePickerButton.setTitle(card.expires, for: .normal)
            securityCodeField.text = card.securityCode
        } else {
            cardNumberField.text = nil
            descriptionField.text = nil
            datePickerButton.setTitle("MM/AAAA", for: .normal)
            securityCodeField.text = nil
        }
        
        saveButtonItem.isEnabled = isValidCard
    }
    
    // MARK: Scroll view delegate
    
    override func scrollViewWillBeginDragging(_ scrollView: UIScrollView) {
        if let selectedIndexPath = tableView.indexPathForSelectedRow {
            tableView.deselectRow(at: selectedIndexPath, animated: true)
        }
        
        cardNumberField.resignFirstResponder()
        descriptionField.resignFirstResponder()
        datePickerButton.resignFirstResponder()
        securityCodeField.resignFirstResponder()
        
        saveButtonItem.isEnabled = isValidCard
    }
    
    // MARK: Table view delegate
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            descriptionField.becomeFirstResponder()
        case 1:
            cardNumberField.becomeFirstResponder()
        case 2:
            datePickerButton.becomeFirstResponder()
        case 3:
            securityCodeField.becomeFirstResponder()
        default:
            break
        }
    }
}

extension CardViewController: UITextFieldDelegate {
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if let selectedIndexPath = tableView.indexPathForSelectedRow {
            tableView.deselectRow(at: selectedIndexPath, animated: true)
        }
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if textField == cardNumberField {
            return cardNumberField.text!.count + string.count - range.length <= 16
        }
        
        if textField == securityCodeField {
            return securityCodeField.text!.count + string.count - range.length <= 3
        }
        
        if textField == descriptionField {
            return true
        }
        
        return false
    }
}
