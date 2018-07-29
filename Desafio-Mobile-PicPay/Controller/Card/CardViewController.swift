//
//  CardViewController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 26/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class CardViewController: UITableViewController, UITextFieldDelegate {

    @IBOutlet weak var cardNumberTextField: UITextField!
    @IBOutlet weak var cardNameTextField: UITextField!
    @IBOutlet weak var cardExpiryDateTextField: UITextField!
    @IBOutlet weak var cardCvvTextField: UITextField!
    @IBOutlet weak var registerButton: UIButton!
    
    var cardNumberTextFieldChecked = false
    var cardNameTextFieldChecked = false
    var cardExpiryDateTextFieldChecked = false
    var cardCvvTextFieldChecked = false
    var headerView: RegisterCreditCardHeaderCell?
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.allowsSelection = false
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.isNavigationBarHidden = true
    }
    
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(section == 0){
            headerView = Bundle.main.loadNibNamed("CreditCardRegisterHeader", owner: self, options: nil)?.first as? RegisterCreditCardHeaderCell
            headerView?.CardViewController = self
            return headerView
        }else{
            return UIView()
        }
    }
    
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(section == 0){
            return 140
        }else{
            return 6
        }
    }
    
    @IBAction func didPressRegisterButton(_ sender: Any) {
        
        cardNumberTextField.endEditing(true)
        cardNameTextField.endEditing(true)
        cardExpiryDateTextField.endEditing(true)
        cardCvvTextField.endEditing(true)
        
        if(cardNumberTextFieldChecked && cardNameTextFieldChecked && cardExpiryDateTextFieldChecked && cardCvvTextFieldChecked){
            saveData()
        }else{
            CustomAlertController.showCustomAlert("Cartão", message: "Favor preencher todos os campos corretamente", delegate: self, handler: nil)
        }
    }
    
    func saveData(){
        guard let cardNumber = cardNumberTextField.text, let cardName = cardNameTextField.text, let cvv = cardCvvTextField.text, let date = cardExpiryDateTextField.text else {return}
        
        appDelegate.coreDataManager.saveCard(cardName, cardNumber: cardNumber, cvv: cvv, date: date)
        NotificationCenter.default.post(name: PaymentViewController.updatePaymentTypeNotificationName, object: self)
        CustomAlertController.showCustomAlert("Cadastrar Cartão", message: "Seu cartão foi cadastrado com sucesso", delegate: self, handler: {
            self.navigationController?.popToRootViewController(animated: true)
        })
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesBegan(touches, with: event)        
    }
}
