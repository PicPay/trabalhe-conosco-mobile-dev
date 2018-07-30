//
//  PaymentViewController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class PaymentViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var userImageVIew: CustomImageView!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var valueTextField: UITextField!
    @IBOutlet weak var footerView: UIView!
    @IBOutlet weak var footerViewBottomAnchor: NSLayoutConstraint!
    @IBOutlet weak var footerViewPaymentButton: UIButton!
    
    var selectedCard: Card?
    var contact: Contact?
    var value: String = ""
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    static let updatePaymentTypeNotificationName = NSNotification.Name(rawValue: "updatePaymentType")
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadViews()
        valueTextField.delegate = self
        NotificationCenter.default.addObserver(self, selector: #selector(moveUpFooterView), name: NSNotification.Name.UIKeyboardWillShow, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(moveDownFooterView), name: NSNotification.Name.UIKeyboardWillHide, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(updatePaymentType), name: PaymentViewController.updatePaymentTypeNotificationName, object: nil)        
        updatePaymentType()
    }
    
    func loadViews(){
        guard let contact = contact else {return}
        userImageVIew.downloadImageView(contact.img)
        usernameLabel.text = contact.username
        nameLabel.text = contact.name
    }
    
    @objc func updatePaymentType(){
        if let card = appDelegate.coreDataManager.fetchSelectedCard(){
            self.selectedCard = card
            guard let number = card.card_number else {return}
            footerViewPaymentButton.setTitle("Cartão \(String(describing: number.suffix(4)))", for: .normal)
        }
    }
    
    @objc func moveUpFooterView(_ notification: Notification){
        if let keyboardFrame: NSValue = notification.userInfo?[UIKeyboardFrameEndUserInfoKey] as? NSValue {
            changeFooterViewBottomConstraint(keyboardFrame.cgRectValue.height-20)
        }
    }
    
    @objc func moveDownFooterView(){
        changeFooterViewBottomConstraint(0)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func changeFooterViewBottomConstraint(_ constant: CGFloat){
        footerViewBottomAnchor.constant =  constant
        UIView.animate(withDuration: 0.5, delay: 0, usingSpringWithDamping: 1, initialSpringVelocity: 0.7, options: .curveEaseOut, animations: {
            self.view.layoutSubviews()
        }, completion: nil)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if(string != ""){
            value = value + string
            textField.text = "\(Strings.R$) \(value.setValueMask())"
            return false
        }else{
            value.removeLast()
            if(value.isEmpty){
                textField.text?.removeAll()
            }else{
                textField.text = "\(Strings.R$) \(value.setValueMask())"
            }            
            return false
        }
    }
    
    @IBAction func didPressCreditCardButton(_ sender: Any) {
        self.performSegue(withIdentifier: Strings.goToCardListController, sender: self)
    }
    
    @IBAction func didPressPaymentButton(_ sender: Any) {
        if let contact = contact, let textValue = valueTextField.text, let card = selectedCard{
            
            let doubleValue = value.getDoubleValue()
            
            if(textValue.isEmpty || doubleValue < 1){
                CustomAlertController.showCustomAlert(Strings.transferencia, message: "Informe um valor válido para transação", delegate: self, handler: nil)
                return
            }
            
            CustomAlertController.showCustomAlert(Strings.transferencia, message: "Confirmando transferencia de \(textValue) para \(contact.name)", delegate: self, cancelAction: true) {
                
                self.appDelegate.customActivityIndicator.showActivityIndicator()
                self.footerViewPaymentButton.isEnabled = false
                self.appDelegate.apiClient.postPaymentData(card: card, value: doubleValue, userID: contact.id, completion: self.paymentHandler)
            }
        }else{
            self.performSegue(withIdentifier: Strings.goToPaymentConfirmation, sender: self)
        }
    }
    
    func paymentHandler(success: Bool, message: String?){
        self.appDelegate.customActivityIndicator.hideActivityIndicator()
        self.footerViewPaymentButton.isEnabled = true
        if(success){
            CustomAlertController.showCustomAlert("", message: "Transferência efetuada com sucesso!", delegate: self, handler: {
                self.navigationController?.popToRootViewController(animated: true)
            })
        }else{
            guard let message = message else {return}
            CustomAlertController.showCustomAlert("Erro ao realizar transferência", message: "\(message)", delegate: self, handler: nil)
        }
    }
}
