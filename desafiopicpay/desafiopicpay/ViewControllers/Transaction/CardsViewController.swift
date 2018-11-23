//
//  CardsViewController.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

class CardsViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var cardButton: UIButton!
    
    var transaction: SendTransaction?
    let segueToNewCard = "SegueToNewCard"
    let segueToSuccess = "SegueToSuccess"
    let cellIdentifier = "CardCellIdentifier"
    var cards = [Card]()
    var selectedCard: Card?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.delegate = self
        tableView.dataSource = self
        
        let radius = Float(cardButton.bounds.height) / 2
        corner(in: cardButton, radius: radius)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        cards = Card.findCards() ?? [Card]()
        tableView.reloadData()
    }
    
    // MARK: - Actions
    
    @IBAction func otherCard(_ sender: Any) {
        self.performSegue(withIdentifier: segueToNewCard, sender: nil)
    }
    
    @IBAction func corfirm(_ sender: Any) {
        guard selectedCard != nil else {
            AlertHelper.showAlert(title: AlertHelper.Title.default, message: "Nenhum cartão selecionado.", viewController: self)
            return
        }
        
        transaction?.cardNumber = CryptoHelper.aesDecrypt(selectedCard?.pan ?? "")
        transaction?.expiryDate = selectedCard?.expiryDate
        transaction?.cvv = Int(selectedCard?.cvv ?? "0")
        
        let interactor = TransactionInteractor()
        interactor.send(transaction: transaction!, success: { (result) in
            DispatchQueue.main.async {
                self.performSegue(withIdentifier: self.segueToSuccess, sender: result)
            }
        }, failure: { (error) in
            AlertHelper.showAlert(title: AlertHelper.Title.default,
                                  message: error.localizedDescription,
                                  viewController: self)
        })
    }

    // MARK: - Functions
    
    func finalNumber(encryptedPan: String) -> String {
        let pan = CryptoHelper.aesDecrypt(encryptedPan) ?? ""
        
        var finalNumbers = ""
        if !pan.isEmpty {
            finalNumbers = pan.substring(from: pan.length - 4)
        }
        
        return finalNumbers
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == segueToNewCard {
            if let destination = segue.destination as? NewCardViewController {
                destination.transaction = transaction
            }
        } else if segue.identifier == segueToSuccess {
            if let destination = segue.destination as? ResultViewController {
                destination.transaction = sender as? TransactionInfo
            }
        }
    }

}

extension CardsViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let cell = tableView.cellForRow(at: indexPath)
        
        selectedCard = cards[indexPath.row]
        cell?.accessoryType = UITableViewCell.AccessoryType.checkmark
    }
    
    func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        let cell = tableView.cellForRow(at: indexPath)
        
        cell?.accessoryType = UITableViewCell.AccessoryType.none
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 65
    }
}

extension CardsViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! CardTableViewCell
        
        let card = cards[indexPath.row]
        let finalNumbers = finalNumber(encryptedPan: card.pan)
        
        cell.cardLabel.text = "Cartão final: \(finalNumbers)"
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cards.count
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
        let card = cards[indexPath.row]
        let finalNumbers = finalNumber(encryptedPan: card.pan)
        
        let cell = tableView.cellForRow(at: indexPath)
        
        let delete = UITableViewRowAction(style: .default, title: "Excluir") { action, index in
            let message = "Tem certeza que deseja excluir o cartão final \(finalNumbers)?"
            AlertHelper.showDialogAlert(title: AlertHelper.Title.deleteCard,
                                        message: message,
                                        viewController: self,
                                        rightBottomButtonText: AlertHelper.Button.delete,
                                        rightButtonHandler: { (action) in
                                            if cell?.accessoryType != .none {
                                                self.selectedCard = nil
                                            }
                                            
                                            Card.delete(card: card)
                                            self.cards.remove(at: indexPath.row)
                                            self.tableView.deleteRows(at: [indexPath], with: UITableView.RowAnimation.automatic)
            })
        }
        
        return [delete]
    }
}
