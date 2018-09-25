//
//  PayViewController.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 18/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class PayViewController: UIViewController {

    var user: User?
    var cards: [Card]?
    weak var viewController: UIViewController?
    weak var userTView : UserTransactionView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .white
        addCustomPayNavBar()
        addPaymentTransactionView()
        addMainCardView()
    }
    
    private func addCustomPayNavBar() {
        let customNavBarFrame = CGRect(x: 0, y: 20, width: self.view.frame.width, height: 44)
        let customPayNavBar = CustomPayNavBar(frame: customNavBarFrame)
        customPayNavBar.viewController = self
        self.view.addSubview(customPayNavBar)
    }

    private func addPaymentTransactionView() {
        let frame = CGRect(x: 0, y: 74, width: self.view.frame.width, height: 104)
        let userTransactionView = UserTransactionView(frame: frame)
        userTransactionView.viewController = self
        userTransactionView.nameLabel.text = user?.name
        userTransactionView.usernameLabel.text = user?.username
        userTransactionView.buttonPay.addTarget(self, action: #selector(PayViewController.pay), for: .touchUpInside)
        self.userTView = userTransactionView
        self.getImage(url: (user?.imageURL)!) { (image) in
            userTransactionView.imageView.image = image
        }
        self.view.addSubview(userTransactionView)
        
    }
    
    private func addMainCardView() {
        self.getMainCard { (card) in
            if card != nil {
                let frame = CGRect(x: 0, y: 178, width: self.view.frame.width, height: 50)
                let customMainCardView = MainCardView(frame: frame)
                if (card?.card_name.isEmpty)! {
                    customMainCardView.nameLabel.text = "Cartão"
                } else {
                    customMainCardView.nameLabel.text = card?.card_name
                }
                customMainCardView.detailLabel.text = "Cartão de final \(card!.card_number.suffix(4))"
                self.view.addSubview(customMainCardView)
            }
        }
    }
}

extension PayViewController {
    
    public func getImage(url: URL, handler: @escaping ((UIImage) -> Void)) {
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard
                let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                let data = data, error == nil,
                let image = UIImage(data: data)
                else { return }
            DispatchQueue.main.async() {
                handler(image)
            }
            }.resume()
    }
    
    private func getMainCard(completion: (Card?) -> Void) {
        if let decodedCards = UserDefaults.standard.object(forKey: "cards") as? Data {
            let cards = NSKeyedUnarchiver.unarchiveObject(with: decodedCards) as! [Card]
            for card in cards {
                if card.isMainCard {
                    completion(card)
                }
            }
        }
    }
    
    private func getValue(completion: (Double) -> Void) {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        if let value = formatter.number(from: self.userTView!.payTextView.text) {
            let amount = value.doubleValue
            print(amount)
            completion(amount)
        }
    }
    
    // Pagar
    @objc private func pay() {
        print("aham ok")
        // verifica se tem um cartão salvo no userdefaults
        if let decodedCards = UserDefaults.standard.object(forKey: "cards") as? Data {
            print("tem cartao salvo")
            // tem cartão salvo
            // verificar cartão válido
            let cards = NSKeyedUnarchiver.unarchiveObject(with: decodedCards) as! [Card]
            for card in cards {
                if card.isMainCard {
                    print("tem principal")
                    let rawValue = self.userTView!.payTextView.text
                    var auxValue = String()
                    if rawValue!.contains(".") {
                        auxValue = rawValue!.replacingOccurrences(of: ".", with: "")
                    } else {
                        auxValue = rawValue!
                    }
                    let almostFinalValue = auxValue.replacingOccurrences(of: ",", with: ".").replacingOccurrences(of: "R$", with: "")
                    let amountValue = Float(almostFinalValue)
                    print("REAL VALOR \(amountValue)")
                    self.requestTransaction(value: amountValue!, card: card, userID: user!.id)
                }
            }
        } else {
            // adicionar cartão -- mostrar alerta
            let alert = UIAlertController(title: "Sem Cartão Salvo", message: "Você não possui um cartão registrado. Por favor, registre um cartão válido na aba de cartões para continuar a transação.", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
    }
    
    private func requestTransaction(value: Float, card: Card, userID : Int) {
        let transactionData : [String: Any] = ["card_number":card.card_number,
                                               "cvv":card.cvv,
                                               "value":value,
                                               "expiry_date":card.expiry_date,
                                               "destination_user_id":userID]
        Alamofire.request("http://careers.picpay.com/tests/mobdev/transaction", method: .post, parameters: transactionData, encoding: JSONEncoding.default).responseJSON { (response) in
            if let result = response.result.value {
                let JSON = result as! NSDictionary
                let transaction = JSON.value(forKey: "transaction") as! [String: Any]
                let success = transaction["success"] as! Bool
                if success {
                    let alert = UIAlertController(title: "Tudo pronto!", message: "Sua transação foi concluída com sucesso ", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                } else {
                    let alert = UIAlertController(title: "Erro", message: "Ocorreu um erro ao tentar completar sua transação. Por favor, verifique se seu cartão é válido e tente novamente.", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                }
            }
        }
        
    }
}
