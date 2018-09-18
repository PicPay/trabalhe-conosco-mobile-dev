//
//  AddCardViewController.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/17/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import UIKit

enum AddCardFieldError {
    case cardNumber
    case expiryDate
}

class AddCardViewController: UIViewController {
    @IBOutlet weak var cardNumberTextField: UITextField! {
        didSet {
            self.cardNumberTextField.placeholder = "Card Number"
            self.cardNumberTextField.keyboardType = .numberPad
            self.cardNumberTextField.delegate = self
            self.cardNumberTextField.addTarget(self, action: #selector(textDidChange(_:)), for: .editingChanged)
        }
    }
    @IBOutlet weak var expiryDateTextField: UITextField! {
        didSet {
            self.expiryDateTextField.placeholder = "Expiry Date"
            self.expiryDateTextField.keyboardType = .numberPad
            self.expiryDateTextField.delegate = self
            self.expiryDateTextField.addTarget(self, action: #selector(textDidChange(_:)), for: .editingChanged)
        }
    }
    
    @IBOutlet weak var addNewCardButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func didTapAddNewCard(_ sender: Any) {
        let (success, fieldError) = self.validateFields()
        if !success, let fieldError = fieldError {
            self.showErrorAlert(fieldError: fieldError)
        } else {
            self.saveCard()
            self.navigationController?.popViewController(animated: true)
        }
    }
    
    func validateFields() -> (Bool, AddCardFieldError?) {
        guard let cardNumber = self.cardNumberTextField.text, cardNumber.count == 19 else {
            return (false, .cardNumber)
        }
        
        guard let expiryDate = self.expiryDateTextField.text, expiryDate.count == 5 else {
            return (false, .expiryDate)
        }
        
        return (true, nil)
    }
    
    func showErrorAlert(fieldError: AddCardFieldError) {
        var alertController: UIAlertController
        switch fieldError {
        case .cardNumber:
            alertController = UIAlertController(title: "Error", message: "Invalid Card Number", preferredStyle: .alert)
        case .expiryDate:
            alertController = UIAlertController(title: "Error", message: "Invalid Expiry Date", preferredStyle: .alert)
        }
        alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: { (alert) in
            switch fieldError {
            case .cardNumber:
                self.cardNumberTextField.becomeFirstResponder()
            case .expiryDate:
                self.expiryDateTextField.becomeFirstResponder()
            }
        }))
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    func saveCard() {
        guard let cardNumberAux = self.cardNumberTextField.text, let expiryDate = self.expiryDateTextField.text else {
            return
        }
        let cardNumber = cardNumberAux.replacingOccurrences(of: " ", with: "")
        
        var dict: [String: Any] = [:]
        dict[CardCoreDataKeys.cardNumber] = cardNumber
        dict[CardCoreDataKeys.expiryDate] = expiryDate
        
        CoreDataService.sharedInstance.addNewEntity(entityName: CardCoreDataKeys.entityName, info: dict)
    }
    
}

extension AddCardViewController: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if textField == self.expiryDateTextField, let text = self.expiryDateTextField.text {
            return text.count < 5 || string.isEmpty ? true : false
        } else if textField == self.cardNumberTextField, let text = self.cardNumberTextField.text {
            return text.count < 19 || string.isEmpty ? true : false
        }
        
        return true
    }
    
    @objc func textDidChange(_ textField: UITextField) {
        if textField == self.expiryDateTextField, let text = self.expiryDateTextField.text {
            var newText = text.replacingOccurrences(of: "/", with: "")
            if text.count > 2 {
                newText.insert("/", at: String.Index.init(encodedOffset: 2))
            }
            
            self.expiryDateTextField.text = newText
        } else if textField == self.cardNumberTextField, let text = self.cardNumberTextField.text {
            let newText = StringUtils.formatCreditCardNumber(text: text)
            self.cardNumberTextField.text = newText
        }
    }
}
