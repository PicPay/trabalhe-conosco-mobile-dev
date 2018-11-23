//
//  NewCardViewController.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 23/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

class NewCardViewController: UIViewController {

    @IBOutlet weak var cardNumberTextField: UITextField!
    @IBOutlet weak var cardValidityTextField: UITextField!
    @IBOutlet weak var cvvTextField: UITextField!
    @IBOutlet weak var confirmButton: UIButton!
    
    var transaction: SendTransaction?
    var cardLength = 0
    let cvvLength = 3
    let segueToSuccess = "SegueToSuccess"
    
    // MARK: - View controller life cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        let radius = Float(confirmButton.bounds.height) / 2
        corner(in: confirmButton, radius: radius)
        
        cvvTextField.delegate = self
        cardNumberTextField.addTarget(self, action: #selector(formatText(sender:)), for: .editingChanged)
        cardValidityTextField.addTarget(self, action: #selector(formatText(sender:)), for: .editingChanged)
    }
    
    // MARK: - Selectors
    
    @objc func formatText(sender: UITextField) {
        var textFormatted = ""
        
        if sender == cardNumberTextField {
            textFormatted = CardHelper.format(creditCardNumber: sender.text)
            
            if CardHelper.matchElo(cardNumber: cardNumberTextField.text!) {
                cardLength = CardHelper.CardLength.Elo
            } else if CardHelper.matchMaster(cardNumber: cardNumberTextField.text!) {
                cardLength = CardHelper.CardLength.Master
            } else if CardHelper.matchVisa(cardNumber: cardNumberTextField.text!) {
                cardLength = CardHelper.CardLength.Visa
            } else if CardHelper.matchDiners(cardNumber: cardNumberTextField.text!) {
                cardLength = CardHelper.CardLength.Diners
            } else if CardHelper.matchAmex(cardNumber: cardNumberTextField.text!) {
                cardLength = CardHelper.CardLength.Amex
            } else if CardHelper.matchHipercard(cardNumber: cardNumberTextField.text!) {
                cardLength = CardHelper.CardLength.Hipercard
            }
        } else if sender == cardValidityTextField {
            textFormatted = CardHelper.formatValidity(validity: sender.text!)
        }
        
        sender.text = textFormatted
    }

    // MARK: - Actions
    
    @IBAction func confirm(_ sender: Any) {
        if validate() {
            let card = Card()
            
            let cardNumber = removeFormatterFromNumbers(stringFormatted: cardNumberTextField.text!)
            card.pan = CryptoHelper.aesEncrypt(cardNumber) ?? ""
            card.expiryDate = cardValidityTextField.text!
            card.cvv = cvvTextField.text!
            
            Card.save(card: card)
            
            transaction?.cardNumber = cardNumber
            transaction?.expiryDate = cardValidityTextField.text!
            transaction?.cvv = Int(cardNumberTextField.text!)
            
            let interactor = TransactionInteractor()
            interactor.send(transaction: transaction!, success: { (result) in
                DispatchQueue.main.async {
                    self.performSegue(withIdentifier: self.segueToSuccess, sender: result)
                }
            }, failure: { (error) in
                AlertHelper.showAlert(title: AlertHelper.Title.default,
                                      message: error.localizedDescription,
                                      viewController: self)
            })
        }
    }
    
    // MARK: - Privates
    
    private func validate() -> Bool {
        let card = removeFormatterFromNumbers(stringFormatted: cardNumberTextField.text!)
        guard card.length >= CardHelper.CardLength.Diners else {
            AlertHelper.showAlert(title: AlertHelper.Title.default, message: "Número do cartão inválido.", viewController: self)
            return false
        }
        
        guard ValidatorHelper.date(cardValidityTextField.text!, format: "MM/yy") else {
            AlertHelper.showAlert(title: AlertHelper.Title.default, message: "Data de validade inválida.", viewController: self)
            return false
        }
        
        guard ValidatorHelper.dateIsGreaterThanToday(cardValidityTextField.text!, format: "MM/yy") else {
            AlertHelper.showAlert(title: AlertHelper.Title.default, message: "Cartão expirado.", viewController: self)
            return false
        }
        
        guard cvvTextField.text!.length == cvvLength else {
            AlertHelper.showAlert(title: AlertHelper.Title.default, message: "Código de segurança (CVV) inválido.", viewController: self)
            return false
        }
        
        return true
     }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == segueToSuccess {
            if let destination = segue.destination as? ResultViewController {
                destination.transaction = sender as? TransactionInfo
            }
        }
    }
    

}

extension NewCardViewController: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if textField.isEqual(cvvTextField) {
            let newString = textField.text! + string
            guard newString.length <= cvvLength else {
                return false
            }
            
            return true
        }
        
        return true
    }
}
