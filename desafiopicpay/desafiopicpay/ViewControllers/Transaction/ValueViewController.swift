//
//  ValueViewController.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

class ValueViewController: UIViewController {

    @IBOutlet weak var titleDescription: UILabel!
    @IBOutlet weak var valueTextField: UITextField!
    @IBOutlet weak var confirmButton: UIButton!
    
    let segueToCards = "SegueToCards"
    let segueToNewCard = "SegueToNewCard"
    var user: User?
    
    // MARK: - View controller life cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        titleDescription.text = "Digite o valor que será enviado para \(user?.name ?? "")."
        
        valueTextField.delegate = self
        valueTextField.addTarget(self, action: #selector(format(sender:)), for: .editingChanged)
        
        let radius = Float(confirmButton.bounds.height) / 2
        corner(in: confirmButton, radius: radius)
    }
    
    // MARK: - Selectors
    
    @objc func format(sender: UITextField) {
        let value = removeFormatterFromNumbers(stringFormatted: sender.text!)
        let decimalNumber = self.decimalNumber(value: value)
        
        sender.text = FormatterHelper.formatCurrency(value: decimalNumber)
    }
    
    // MARK: - Privates
    
    private func validate() -> Bool {
        let value = valueTextField.text!
        let valueInNumbers = Int(removeFormatterFromNumbers(stringFormatted: valueTextField.text!)) ?? 0
        
        guard !value.isEmpty && valueInNumbers > 0 else {
            let message = "Valor inválido."
            AlertHelper.showAlert(title: AlertHelper.Title.none, message: message, viewController: self)
            return false
        }
        
        return true
    }
    
    func decimalNumber(value: String) -> NSDecimalNumber {
        var decimalNumber = NSDecimalNumber(string: value)
        if decimalNumber.compare(NSNumber(value: 0)) == ComparisonResult.orderedDescending {
            decimalNumber = decimalNumber.dividing(by: hundred)
        }
        
        return decimalNumber
    }
    
    // MARK: - Actions
    
    @IBAction func confirm(_ sender: Any) {
        if validate() {
            var transaction = SendTransaction()
            transaction.destinationUserId = user?.id
            
            let value = removeFormatterFromNumbers(stringFormatted: valueTextField.text!)
            let decimalNumber = self.decimalNumber(value: value)
            
            transaction.value = decimalNumber.floatValue
            
            let cards = Card.findCards() ?? [Card]()

            if cards.isEmpty {
                self.performSegue(withIdentifier: segueToNewCard, sender: transaction)
            } else {
                self.performSegue(withIdentifier: segueToCards, sender: transaction)
            }
            
            
        }
    }
    
    @IBAction func dismiss(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == segueToCards {
            if let destination = segue.destination as? CardsViewController {
                destination.transaction = sender as? SendTransaction
            }
        } else if segue.identifier == segueToNewCard {
            if let destination = segue.destination as? NewCardViewController {
                destination.transaction = sender as? SendTransaction
            }
        }
    }
}

// MARK: - Extensions

extension ValueViewController: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let newString = textField.text! + string
        guard newString.length <= currencyValidationMaxLength else {
            return false
        }
        
        return true
    }
}
