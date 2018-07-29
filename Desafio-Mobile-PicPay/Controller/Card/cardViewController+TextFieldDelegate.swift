//
//  cardViewController+TextFieldDelegate.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 26/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

extension CardViewController{
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        
        switch textField.tag {
        case 0:
            cardNumberTextFieldChecked = checkTextField(size: 6, message: "Cartão inválido", textField: cardNumberTextField)
            self.setCardNumberLabel(textField.text)
        case 1:
            cardNameTextFieldChecked = checkTextField(size: 5, message: "Favor preencher corretamente todos os campos", textField: cardNameTextField)
            headerView?.cardNameLabel.text = textField.text
        case 2:
            cardExpiryDateTextFieldChecked = checkTextField(size: 5, message: "Data inválida", textField: cardExpiryDateTextField)
            headerView?.cardDateLabel.text = textField.text
        case 3:
            cardCvvTextFieldChecked = checkTextField(size: 3, message: "Código de verificação inválido", textField: cardCvvTextField)
        default:
            break
        }
    }
    
    func setCardNumberLabel(_ text: String?){
        guard let text = text else {return}
        if(!text.isEmpty){
            headerView?.cardNumberLabel.text = text
            headerView?.cardNumberLabel.font = UIFont.systemFont(ofSize: 15)            
        }
    }
    
    func checkTextField(size: Int, message: String, textField: UITextField)->Bool{
        guard let text = textField.text else {return false}
        if(text.count < size){
            textField.textColor = .red
            CustomAlertController.showCustomAlert("Cadastrar Cartão", message: message, delegate: self, handler: nil)
            return false
        }
        return true
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        textField.textColor = .black
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if(textField.tag == 2 && string != ""){
            guard let text = textField.text else {return false}
            if (text.count < 5){
                if(text.count == 2){
                    cardExpiryDateTextField.text = "\(text)/"
                }
                return true
            }
            return false
        }else if (textField.tag == 0){
            if(textField.text?.count == 19 && string != ""){
                return false
            }else{
                if((textField.text?.count == 4 || textField.text?.count == 9 || textField.text?.count == 14) && string != ""){
                    textField.text?.append(" ")
                }
            }
        }else if (textField.tag == 3){
            if (textField.text?.count == 3 && string != ""){
                return false
            }else if(textField.text?.count == 2 && string != "" ){
                textField.text?.append(string)
                self.tableView.endEditing(true)
            }
        }
        return true
    }
}
