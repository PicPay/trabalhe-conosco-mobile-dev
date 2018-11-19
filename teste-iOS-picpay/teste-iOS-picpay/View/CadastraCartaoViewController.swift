//
//  CadastraCartaoViewController.swift
//  teste-iOS-picpay
//
//  Created by Bruno Lopes de Mello on 18/11/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import UIKit

protocol CadastraCartaoDelegate {
    func mensagemSucesso()
}

class CadastraCartaoViewController: UIViewController {

    @IBOutlet weak var numeroTextField: UITextField!
    @IBOutlet weak var cvvTextField: UITextField!
    @IBOutlet weak var validadeTextField: UITextField!
    @IBOutlet weak var salvarButton: UIButton!
    
    var delegate: CadastraCartaoDelegate!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        salvarButton.isEnabled = false
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func salvarCartao(_ sender: Any) {
        if let numero = numeroTextField.text, let cvv = cvvTextField.text,
            let validade = validadeTextField.text {
            if CoreDataCartaoManager.salvaCartao(numero: numero, cvv: Int(cvv) ?? 000, expiryDate: validade) {
                dismiss(animated: true) {
                    self.delegate.mensagemSucesso()
                }
            }
        }
    }
    
    @IBAction func cancelar(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
    func validaCampos() {
        if numeroTextField.text?.count ?? 0 > 18 &&
            cvvTextField.text?.count ?? 0 > 2 &&
             validadeTextField.text?.count ?? 0 > 4 {
            salvarButton.isEnabled = true
        } else {
            salvarButton.isEnabled = false
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        view.endEditing(true)
    }
    
}

extension CadastraCartaoViewController: UITextFieldDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        validaCampos()
        
        let newString = (textField.text! as NSString).replacingCharacters(in: range, with: string)
        
        if textField == numeroTextField {
            let replacementStringIsLegal = string.rangeOfCharacter(from: NSCharacterSet(charactersIn: "0123456789").inverted) == nil
            
            if !replacementStringIsLegal
            {
                return false
            }
            
            let components = newString.components(separatedBy: NSCharacterSet(charactersIn: "0123456789").inverted)
            let decimalString = components.joined(separator: "") as NSString
            let length = decimalString.length
            
            let hasLeadingOne = length > 0 && decimalString.character(at: 0) == (1 as unichar)
            
            if length == 0 || (length > 16 && !hasLeadingOne) || length > 19
            {
                let newLength = (textField.text! as NSString).length + (string as NSString).length - range.length as Int
                
                return (newLength > 16) ? false : true
            }
            var index = 0 as Int
            let formattedString = NSMutableString()
            
            if hasLeadingOne
            {
                formattedString.append("1 ")
                index += 1
            }
            if length - index > 4
            {
                let prefix = decimalString.substring(with: NSMakeRange(index, 4))
                formattedString.appendFormat("%@ ", prefix)
                index += 4
            }
            
            if length - index > 4
            {
                let prefix = decimalString.substring(with: NSMakeRange(index, 4))
                formattedString.appendFormat("%@ ", prefix)
                index += 4
            }
            if length - index > 4
            {
                let prefix = decimalString.substring(with: NSMakeRange(index, 4))
                formattedString.appendFormat("%@ ", prefix)
                index += 4
            }
            
            
            let remainder = decimalString.substring(from: index)
            formattedString.append(remainder)
            textField.text = formattedString as String
            return false
        }
        
        if textField == cvvTextField {
            if newString.count >= 4 {
                return false
            }
            return true
        }
        
        if textField == validadeTextField {
            let components = newString.components(separatedBy: NSCharacterSet(charactersIn: "0123456789").inverted)
            let decimalString = components.joined(separator: "") as NSString
            let length = decimalString.length
            
            let hasLeadingOne = length > 0 && decimalString.character(at: 0) == (1 as unichar)
            
            if length == 0 || (length > 4 && !hasLeadingOne) || length > 5
            {
                let newLength = (textField.text! as NSString).length + (string as NSString).length - range.length as Int
                
                return (newLength > 4) ? false : true
            }
            var index = 0 as Int
            let formattedString = NSMutableString()
            
            if hasLeadingOne
            {
                formattedString.append("1 ")
                index += 1
            }
            if length - index > 2
            {
                let prefix = decimalString.substring(with: NSMakeRange(index, 2))
                formattedString.appendFormat("%@/", prefix)
                index += 2
            }
            let remainder = decimalString.substring(from: index)
            formattedString.append(remainder)
            textField.text = formattedString as String
            return false
        }
        
        return true
    }
}
