//
//  AddCardViewController.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 29/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class AddCardViewController: UIViewController {
    
    @IBOutlet weak var cardBrand: UITextField!
    @IBOutlet weak var name: UITextField!
    @IBOutlet weak var cardNumber: TextInputField!
    @IBOutlet weak var expireDate: TextInputField!
    @IBOutlet weak var cardVerificationCode: TextInputField!
    @IBOutlet weak var cep: TextInputField!
    
    let cardNumberFormatter = TextInputFormatter(textPattern: "#### #### #### ####")
    let expireDateFormatter = TextInputFormatter(textPattern: "##/####")
    let cardVerificationCodeFormatter = TextInputFormatter(textPattern: "###")
    let cepFormatter = TextInputFormatter(textPattern: "#####-###")
    
    let cardNumberController = TextInputController()
    let expireDateController = TextInputController()
    let cardVerificationCodeController = TextInputController()
    let cepController = TextInputController()
    
    var dialog: UIAlertController?
    var errorDialog: UIAlertController?
    
    @IBOutlet weak var addCardButton: UIRoundedButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupFields()
        setupDialogs()
        cardBrand.rightViewMode = .always
        let arrow = UIImageView(frame: CGRect(x: 0, y: 0, width: 20, height: 20))
        arrow.image = UIImage(named: "ic_arrow_drop_down")
        arrow.tintColor = Colors.mediumGreen
        cardBrand.rightView = arrow
        cardBrand.tintColor = UIColor.clear
        addCardButton.addTarget(self, action: #selector(addCard), for: .touchUpInside)
    }
    
    func setupFields(){
        cardNumberFormatter.allowedSymbolsRegex = "[0-9]"
        expireDateFormatter.allowedSymbolsRegex = "[0-9]"
        cardVerificationCodeFormatter.allowedSymbolsRegex = "[0-9]"
        cepFormatter.allowedSymbolsRegex = "[0-9]"
        
        cardNumberController.textInput = cardNumber
        cardNumberController.formatter = cardNumberFormatter
        expireDateController.textInput = expireDate
        expireDateController.formatter = expireDateFormatter
        cardVerificationCodeController.textInput = cardVerificationCode
        cardVerificationCodeController.formatter = cardVerificationCodeFormatter
        cepController.textInput = cep
        cepController.formatter = cepFormatter
    }
    
    @IBAction func chooseBrand(_ sender: Any) {
        cardBrand.endEditing(true)
        self.present(dialog!, animated: true, completion: nil)
    }
    
    func setupDialogs(){
        dialog = UIAlertController(
            title: "choose_brand".localized,
            message: nil,
            preferredStyle: .alert)
        let action1 = UIAlertAction(title: "Visa", style: .default, handler: { [weak self] (action) -> Void in
            guard let strongSelf = self else {return}
            strongSelf.cardBrand.text = "Visa"
        })
        let action2 = UIAlertAction(title: "Mastercard", style: .default, handler: { [weak self] (action) -> Void in
            guard let strongSelf = self else {return}
            strongSelf.cardBrand.text = "Mastercard"
        })
        let cancelAction = UIAlertAction(title: "cancel".localized, style: .destructive, handler: { (action) -> Void in })
        dialog?.addAction(action1)
        dialog?.addAction(action2)
        dialog?.addAction(cancelAction)
        
        errorDialog = UIAlertController(title: "error".localized, message: nil, preferredStyle: .alert)
        errorDialog?.addAction(UIAlertAction(title: "ok".localized, style: .default, handler: nil))
    }
    
    func isInputValid() -> Bool{
        if (cardBrand.text?.isEmpty)! {
            errorDialog?.message = String(format: "field_incorrect".localized, "card_brand".localized)
            return false
        }
        if (name.text?.isEmpty)! {
            errorDialog?.message = String(format: "field_incorrect".localized, "name_card".localized)
            return false
        }
        if (cardNumber.content?.isEmpty)! || cardNumber.content?.digitsOnly().count != 16 {
            errorDialog?.message = String(format: "field_incorrect".localized, "card_number".localized)
            return false
        }
        if (expireDate.content?.isEmpty)! || expireDate.content?.digitsOnly().count != 6{
            errorDialog?.message = String(format: "field_incorrect".localized, "expire_date".localized)
            return false
        } else {
            let month = Int(expireDate.content!.digitsOnly()[0...1])!
            let year = Int(expireDate.content!.digitsOnly()[2...5])!
            let date = Date()
            let calendar = Calendar.current
            let currentYear = calendar.component(.year, from: date)
            if month < 1 || month > 12 || year < currentYear {
                errorDialog?.message = String(format: "field_incorrect".localized, "expire_date".localized)
                return false
            }
        }
        if (cardVerificationCode.content?.isEmpty)! || cardVerificationCode.content?.digitsOnly().count != 3 {
            errorDialog?.message = String(format: "field_incorrect".localized, "cvc".localized)
            return false
        }
        if (cep.content?.isEmpty)! || cep.content?.digitsOnly().count != 8 {
            errorDialog?.message = String(format: "field_incorrect".localized, "cep".localized)
            return false
        }
        return true
    }
    
    @objc func addCard(){
        if isInputValid() {
            //add Card to DB
        } else {
            self.present(errorDialog!, animated: true, completion: nil)
        }
    }
}
