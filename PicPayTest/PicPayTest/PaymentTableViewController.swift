//
//  PaymentTableViewController.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 16/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import UIKit
import Alamofire

protocol TextFieldDelegate: class {
    func getValueText() -> String
}

class PaymentTableViewController: UITableViewController {
    
    var userSelected: User?
    var cartoes: [Cartao] = []
    var cartSelected: Cartao?
    weak var delegate: TextFieldDelegate?
    var rowSelected: Int = 0
    
    let dataManager = DataManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        self.cartoes = dataManager.getCartoes() ?? []
        self.tableView.reloadData()
    }
    
    
    
    @IBAction func enviar(_ sender: Any) {
        if let cart = cartSelected {
            if let user = userSelected {
                if let valor = self.delegate?.getValueText().removeFormatacaoMonetaria() {
                    if let valorDouble = Float(valor) {
                        self.enviarPayment(cart: cart, user: user, valor: valorDouble)
                    }
                } else {
                    let alert = UIAlertController(title: "Atenção", message: "É necessário informar o valor do pagamento antes de continuar", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                }
            }
        } else {
            let alert = UIAlertController(title: "Atenção", message: "É necessário selecionar uma forma de pagamento antes de continuar", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
    }
    
    func enviarPayment(cart: Cartao, user: User, valor: Float) {
        if let cvv = cart.cvv {
            if let cvv = Int(cvv) {
                let requisicao = "http://careers.picpay.com/tests/mobdev/transaction"
                let numeroSWS = cart.numero!.replacingOccurrences(of: " ", with: "")
                let parametros: [String: Any] = ["card_number":numeroSWS,
                                                 "cvv": cvv,
                                                 "value": valor,
                                                 "expiry_date": cart.vencimento!,
                                                 "destination_user_id": user.id]
                
                Alamofire.request(requisicao, method: .post, parameters: parametros, encoding: JSONEncoding.default).responseJSON { (response) in
                    print(requisicao)
                    print(parametros)
                    if let data = response.data {
                        if let transaction = try? JSONDecoder().decode(Transaction.self, from: data) {
                            if let transcation = transaction.transaction {
                                if transcation.success! {
                                    let alert = UIAlertController(title: "Successo!", message: "Seu pagamento foi enviado com sucesso!", preferredStyle: .alert)
                                    alert.addAction(UIAlertAction(title: "Ok!", style: .default, handler: { (action) in
                                        self.navigationController?.popToRootViewController(animated: true)
                                    }))
                                    self.present(alert, animated: true, completion: nil)
                                    
                                } else {
                                    let alert = UIAlertController(title: "Falhou!", message: "Não foi possível enviar seu pagamento", preferredStyle: .alert)
                                    alert.addAction(UIAlertAction(title: "Tentar Novamente", style: .default, handler: { (action) in
                                        self.enviarPayment(cart: cart, user: user, valor: valor)
                                    }))
                                    self.present(alert, animated: true, completion: nil)
                                    
                                }
                            }
                        } else {
                            let alert = UIAlertController(title: "Atenção", message: "Erro no parse", preferredStyle: .alert)
                            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
                            self.present(alert, animated: true, completion: nil)
                        }
                    }
                }
            }
        }
    }
    
    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 3
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        if section != 2 {
            return 1
        } else {
            return self.cartoes.count + 1
        }
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "userCell", for: indexPath) as? UsersListTableViewCell
            cell?.setupCell(user: userSelected!)
            return cell!
        } else if indexPath.section == 1 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "valueCell", for: indexPath) as? ValorTableViewCell
            self.delegate = cell
            cell?.setupCell(self)
            return cell!
        } else if indexPath.section == 2 {
            if self.cartoes.count > 0 && indexPath.row <= self.cartoes.count - 1 {
                let cell = tableView.dequeueReusableCell(withIdentifier: "cartCell", for: indexPath) as? CartoesTableViewCell
                cell?.setupCell(cartao: cartoes[indexPath.row])
                return cell!
            }
            let cell = tableView.dequeueReusableCell(withIdentifier: "newCartCell", for: indexPath) as? CartoesNewTableViewCell
            return cell!
        }
        return UITableViewCell()
    }
    
//    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
//        return
//    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        if section == 1 {
            return "VALOR DO PAGAMENTO"
        } else if section == 2 {
            return "FORMA DE PAGAMENTO"
        }
        return ""
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        if indexPath.section == 0 {
            self.navigationController?.popViewController(animated: true)
        }
        if indexPath.section == 2 {
            let cell = tableView.cellForRow(at: indexPath)
            if let cellCart = cell as? CartoesTableViewCell {
                if cellCart.accessoryType == UITableViewCell.AccessoryType.none {
                    cellCart.accessoryType = .checkmark
                    self.cartSelected = self.cartoes[indexPath.row]
                    self.rowSelected = indexPath.row
                } else {
                    cellCart.accessoryType = .none
                    self.cartSelected = nil
                }
            } else {
                performSegue(withIdentifier: "addCartSegue", sender: self)
            }
        }
    }
    
    override func tableView(_ tableView: UITableView, willSelectRowAt indexPath: IndexPath) -> IndexPath? {
            var index = 0
            for row in tableView.indexPathsForVisibleRows! {
                if row.row == self.rowSelected {
                    tableView.cellForRow(at: IndexPath(row: index, section: 2))?.accessoryType = .none
                }
                index = index + 1
            }
        return indexPath
}
}
extension PaymentTableViewController: UITextFieldDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if !string.isEmpty {
            let texto = textField.text! + string
            textField.text = texto.currencyInputFormatting()
            return false
        } else if string.isEmpty {
            let texto = textField.text!
            textField.text = texto.currencyInputFormatting()
        }
        return true
    }
}
