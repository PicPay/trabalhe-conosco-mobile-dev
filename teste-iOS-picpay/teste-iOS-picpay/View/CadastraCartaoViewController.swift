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
            if CoreDataCartaoManager.salvaCartao(numero: numero, cvv: cvv, expiryDate: validade) {
                dismiss(animated: true) {
                    self.delegate.mensagemSucesso()
                }
            }
        }
    }
    
    @IBAction func cancelar(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
    func validaCampos() -> Bool {
        
        return false
    }
}

extension CadastraCartaoViewController: UITextFieldDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        return true
    }
}
