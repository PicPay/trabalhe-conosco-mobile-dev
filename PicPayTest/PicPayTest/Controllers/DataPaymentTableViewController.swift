//
//  DataPaymentTableViewController.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 14/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import UIKit

class DataPaymentTableViewController: UITableViewController {
    
    @IBOutlet weak var numCartaoTxtFld: UITextField!
    @IBOutlet weak var vencCartaoTxtFld: UITextField!
    @IBOutlet weak var cvvCartaoTxtFld: UITextField!
    
    let dataManager = DataManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.numCartaoTxtFld.delegate = self
        self.cvvCartaoTxtFld.delegate = self
        self.vencCartaoTxtFld.delegate = self
        
        self.vencCartaoTxtFld.placeholder = "00/00"
    }

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
       return 3
    }
    
    func saveNewCart(numero: String, vencimento: String, cvv: String) {
        dataManager.saveCartao(withnumero: numero, eCvv: cvv, eVencimento: vencimento)
    }
    
    
    @IBAction func salvar(_ sender: Any) {
        if let numero = numCartaoTxtFld.text, let vencimento = vencCartaoTxtFld.text, let cvv =  cvvCartaoTxtFld.text {
            
            if numero.isEmpty || vencimento.isEmpty || cvv.isEmpty {
                let alert = UIAlertController(title: "Atenção", message: "Todos os campos são obrigatórios!", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
                present(alert, animated: true, completion: nil)
            } else {
                self.saveNewCart(numero: numero, vencimento: vencimento, cvv: cvv)
                self.navigationController?.popViewController(animated: true)
            }
        }
    }
    
}
extension DataPaymentTableViewController: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if textField == numCartaoTxtFld {
            
            if !string.isEmpty {
                if textField.text!.count <= 18 {
                    var stringAux = textField.text! + string
                    if stringAux.count == 5 || stringAux.count == 10 || stringAux.count == 15 {
                        stringAux = textField.text! + " " + string
                    }
                    textField.text = stringAux
                }
                return false
            }
            return true
        } else if textField == cvvCartaoTxtFld {
            if !string.isEmpty {
                if textField.text!.count <= 2 {
                    textField.text = textField.text! + string
                }
                return false
            }
        } else if textField == vencCartaoTxtFld {
            if !string.isEmpty {
                if textField.text!.count <= 4 {
                    var stringAux = textField.text! + string
                    if stringAux.count == 3 {
                        stringAux = textField.text! + "/" + string
                    }
                    textField.text = stringAux
                }
                return false
            }
        }
        return true
    }
}

