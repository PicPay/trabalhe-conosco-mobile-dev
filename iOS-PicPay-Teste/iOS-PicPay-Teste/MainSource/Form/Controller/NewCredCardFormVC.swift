//
//  NewCredCardFormVC.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit
import CoreData

class NewCredCardFormVC: BaseTableViewCStatic {
    
    //Replace the color of next viewcontroller and change he's color to green
    override func viewWillDisappear(_ animated: Bool) {
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: nil, action: nil)
        navigationItem.backBarButtonItem?.tintColor = .lightGreen
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        let credcard = cred.FetchRequest()
        if credcard?.numero != nil {
            self.form.dateCredcard.text = credcard?.vencimento
            self.form.cvvCredcard.text = credcard?.cvv
            self.form.nameOwnerCredcard.text = credcard?.nome
            self.form.numberCredcard.text = credcard?.numero
            self.form.configureNotEmpty()
            self.isEditable = true
            self.saveViewB.button.isHidden = false
        }
    }
    
    
    //Constants
    var contato: Contato!
    var delegate: CheckViewControllerProtocol!
    
    let saveViewB: ViewButton = {
        let view = ViewButton()
        view.button.addTarget(self, action: #selector(getDataFromForm), for: .touchUpInside)
        view.button.setTitle("Salvar", for: .normal)
        view.button.isHidden = true
        return view
    }()
    let cred = CredCoreData()
    var isEditable = false
    let form: FormView = {
        let fview = FormView()
        return fview
    }()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        ConfigureFormVC()
    }
    
    
    //MARK:- Validation Form
    @objc fileprivate func getDataFromForm() {
    
        let nome = self.form.nameOwnerCredcard.text
        let numero = self.form.numberCredcard.text
        let cvv = self.form.cvvCredcard.text
        let vencimento = self.form.dateCredcard.text
        
        if nome != "", numero != "", cvv != "", vencimento != "" {
            let cred = CredCoreData()
            cred.credcard.nome = nome
            cred.credcard.numero = numero
            cred.credcard.cvv = cvv
            cred.credcard.vencimento = vencimento
            cred.save()
            self.goPaymentView()
        }
    }

    
    //MARK:- Call PaymentView
    fileprivate func goPaymentView() {
        if !self.isEditable {
            let viewc = PaymentViewController()
            if !self.isEditable {
                viewc.contato = self.contato
                viewc.delegate = self.delegate
            }
            
            //Just Remove FormViewController from the hierarchy
            var viewControllers = navigationController?.viewControllers
            viewControllers?.removeLast(2)
            viewControllers?.append(viewc)
            navigationController?.setViewControllers(viewControllers!, animated: true)
        } else {
            self.navigationController?.popViewController(animated: true)
        }
    }
    
    
    //MARK:- Configure ViewController Form
    fileprivate func ConfigureFormVC() {
        self.title = "Cadastrar cartão"
        self.view.backgroundColor = .strongBlack
        self.navigationItem.titleView = UIView()
    }
    
    
    //MARK:- Make the bottom tabbar
    override var canBecomeFirstResponder: Bool {
        return true
    }
    override var inputAccessoryView: UIView? {
        return self.saveViewB
    }
    
}


extension NewCredCardFormVC: UITextFieldDelegate {
    
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        let str = textField.text! + string
        
        switch textField {
        case self.form.numberCredcard:
            if string != "" {
                textField.text = str.maskNumberCred()
                return false
            } else {
                if textField.text!.last == " " {
                    textField.text?.removeLast()
                }
                return true
            }
        case self.form.cvvCredcard:
            if str.count > 3 {
                return false
            }
            return true
        case self.form.dateCredcard:
            if string != "" {
                textField.text = str.maskDateExperence()
                return false
            }
            return true
        default:
            return true
        }
    }
    
    
    //EVENT:- Start Animation to placeholder
    func textFieldDidEndEditing(_ textField: UITextField) {
        if let textF = textField as? DefaultTextField {
            textF.animationToDown()
        }
    }
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if let textF = textField as? DefaultTextField {
            textF.animationToTop()
            if textF == self.form.cvvCredcard {
                self.saveViewB.button.isHidden = false
            }
        }
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
}
