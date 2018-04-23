//
//  CreditCardTableViewController.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import UIKit

class CreditCardTableViewController: UITableViewController {

    @IBOutlet weak var numberTextField: UITextField!
    @IBOutlet weak var expiredDateTextField: UITextField!
    @IBOutlet weak var cvvTextField: UITextField!
    @IBOutlet weak var saveButton: UIButton!
    
    let viewModel: CreditCardViewModel = CreditCardViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.estimatedRowHeight = 40
        tableView.rowHeight = UITableViewAutomaticDimension
        saveButton.layer.cornerRadius = saveButton.frame.height / 2
        saveButton.backgroundColor = ColorConstant.primary
        saveButton.tintColor = UIColor.white
        
        expiredDateTextField.text = viewModel.expiredDate ?? ""
        numberTextField.text = viewModel.number != nil ? "\(viewModel.number!)" : ""
        cvvTextField.text = viewModel.cvv != nil ? "\(viewModel.cvv!)" : ""
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func saveButtonTapped(_ sender: Any) {
        do {
            if try viewModel.saveAndValidationForm() {
                
            }
        } catch (let error) {
            switch error {
            case FormCreditCardError.cvvIsRequired:
                showErrorMessage("Campo de número de segurança é obrigatório.")
                break
            case FormCreditCardError.expiredDateIsRequired:
                showErrorMessage("Campo de data de expiração é obrigatório.")
                break
            case FormCreditCardError.invalidExpiredDate:
                showErrorMessage("Data de validade não é válida.")
                break
            case FormCreditCardError.numberIsRequired:
                showErrorMessage("Número do cartão é obrigatório.")
                break
            case FormCreditCardError.notSaveData:
                showErrorMessage("Ocorreu um erro desconhecido ao tentar salvar os dados do seu cartão.")
                break
            default:
                showErrorMessage("Ocorreu um erro desconhecido ao tentar salvar os dados do seu cartão.")
            }
        }
    }
    
}

extension CreditCardTableViewController: UITextFieldDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if textField == self.numberTextField {
            let mask: String = "#### #### #### ####"
            if !mask.isEmpty && textField.text != nil {
                textField.text = viewModel.maskForTextField(textField: textField, string: string, mask: mask, range: range)
                numberTextField.text = textField.text
                viewModel.number = Int64(textField.text?.replacingOccurrences(of: " ", with: "") ?? "") ?? 0
            } else {
                return (textField.text != nil)
            }
        }
        if textField == self.expiredDateTextField {
            let mask: String = "##/##"
            if !mask.isEmpty && textField.text != nil {
                textField.text = viewModel.maskForTextField(textField: textField, string: string, mask: mask, range: range)
                expiredDateTextField.text = textField.text
                viewModel.expiredDate = textField.text
            } else {
                return (textField.text != nil)
            }
        }
        if textField == self.cvvTextField {
            let mask: String = "###"
            if !mask.isEmpty && textField.text != nil {
                textField.text = viewModel.maskForTextField(textField: textField, string: string, mask: mask, range: range)
                cvvTextField.text = textField.text
                viewModel.cvv = Int(textField.text ?? "") ?? 0
            } else {
                return (textField.text != nil)
            }
        }
        
        return false
    }
    
}

extension CreditCardTableViewController {

    func showErrorMessage(_ message: String) {
        let alert = UIAlertController(title: "Atenção", message: message, preferredStyle: .alert)
        let action = UIAlertAction(title: "Ok", style: .default, handler: nil)
        alert.addAction(action)
        self.present(alert, animated: true, completion: nil)
    }
}
