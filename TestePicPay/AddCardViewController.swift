//
//  AddCardViewController.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 29/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class AddCardViewController: UIViewController{
    
    @IBOutlet weak var cardBrand: UITextField!
    @IBOutlet weak var name: UITextField!
    @IBOutlet weak var cardNumber: TextInputField!
    @IBOutlet weak var expiryDate: UITextField!
    @IBOutlet weak var cardVerificationCode: TextInputField!
    @IBOutlet weak var cep: TextInputField!
    @IBOutlet weak var addCardButton: UIRoundedButton!
    
    let cardNumberFormatter = TextInputFormatter(textPattern: "#### #### #### ####")
    let cardVerificationCodeFormatter = TextInputFormatter(textPattern: "###")
    let cepFormatter = TextInputFormatter(textPattern: "#####-###")
    
    let cardNumberController = TextInputController()
    let cardVerificationCodeController = TextInputController()
    let cepController = TextInputController()
    
    var dialog: UIAlertController?
    var errorDialog: UIAlertController?
    var expiryDatePicker: MonthYearPickerView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupFields()
        setupDialogs()
        setupPicker()
    }
    
    func setupFields(){
        cardNumberFormatter.allowedSymbolsRegex = "[0-9]"
        cardVerificationCodeFormatter.allowedSymbolsRegex = "[0-9]"
        cepFormatter.allowedSymbolsRegex = "[0-9]"
        
        cardNumberController.textInput = cardNumber
        cardNumberController.formatter = cardNumberFormatter
        cardVerificationCodeController.textInput = cardVerificationCode
        cardVerificationCodeController.formatter = cardVerificationCodeFormatter
        cepController.textInput = cep
        cepController.formatter = cepFormatter
        
        let arrow1 = UIImageView(frame: CGRect(x: 0, y: 0, width: 20, height: 20))
        arrow1.image = UIImage(named: "ic_arrow_drop_down")
        arrow1.tintColor = Colors.mediumGreen
        
        cardBrand.rightViewMode = .always
        cardBrand.rightView = arrow1
        cardBrand.tintColor = UIColor.clear
        
        let arrow2 = UIImageView(frame: CGRect(x: 0, y: 0, width: 20, height: 20))
        arrow2.image = UIImage(named: "ic_arrow_drop_down")
        arrow2.tintColor = Colors.mediumGreen
        
        expiryDate.rightViewMode = .always
        expiryDate.rightView = arrow2
        expiryDate.tintColor = UIColor.clear
        
        addCardButton.addTarget(self, action: #selector(addCard), for: .touchUpInside)
    }
    
    func setupPicker(){
        expiryDatePicker = MonthYearPickerView(frame: CGRect(x: 0, y: self.view.frame.size.height - 170, width: self.view.frame.size.width, height: 170))
        expiryDatePicker.onDateSelected = { [weak self] (month: Int, year: Int) in
            guard let strongSelf = self else {return}
            let string = String(format: "%02d/%d", month, year)
            strongSelf.expiryDate.text = string
        }
        expiryDatePicker.backgroundColor = UIColor.white
        expiryDatePicker.isHidden = true
        view.addSubview(expiryDatePicker)
    }
    
    @IBAction func chooseBrand(_ sender: Any) {
        cardBrand.endEditing(true)
        self.present(dialog!, animated: true, completion: nil)
    }
    
    @IBAction func pickDate(_ sender: Any) {
        expiryDate.endEditing(true)
        expiryDatePicker.isHidden = false
        let doneButton = UIBarButtonItem(barButtonSystemItem: .done, target: self, action: #selector(donePickingDate))
        self.navigationItem.setRightBarButton(doneButton, animated: true)
    }
    
    @objc func donePickingDate(){
        self.navigationItem.rightBarButtonItem = nil
        let string = String(format: "%02d/%d", expiryDatePicker.month, expiryDatePicker.year)
        expiryDate.text = string
        expiryDatePicker.isHidden = true
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
        if (expiryDate.text?.isEmpty)! || expiryDate.text?.digitsOnly().count != 6{
            errorDialog?.message = String(format: "field_incorrect".localized, "expire_date".localized)
            return false
        } else {
            let monthAndYear = expiryDate.text!.elementsFromExpiryDate()
            let month = monthAndYear.0
            let year = monthAndYear.1
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
            let brand = CreditCardBrand(rawValue: cardBrand.text!)!
            let formatter = DateFormatter()
            formatter.dateFormat = "dd/MM/yyyy"
            let monthAndYear = expiryDate.text!.elementsFromExpiryDate()
            let date = formatter.date(from: "\(01)/\(monthAndYear.0)/\(monthAndYear.1)")
            let card = CreditCard(brand: brand, name: name.text!, number: cardNumber.content!, expireDate: date!, cvc: cardVerificationCode.content!, cep: cep.content!)
            
            let database = DBUtil.shared
            database.addCard(card)
        
        } else {
            self.present(errorDialog!, animated: true, completion: nil)
        }
    }
}
