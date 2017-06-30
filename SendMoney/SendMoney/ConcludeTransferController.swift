//
//  ConcludeTransferController.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 28/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import UIKit
import SVProgressHUD

class ConcludeTransferController: UITableViewController {
    
    var usersSelected: [UserProfile] = [UserProfile]()
    var cardInfoDataSource: [CardInfo] = [CardInfo]()
    var myCards: [CreditCard] = [CreditCard]()
    var valueToSent: Double = 0.0

    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = Localize.NewTransfer.rawValue.localized

        tableView.rowHeight = UITableViewAutomaticDimension;
        tableView.estimatedRowHeight = 100.0
        
        SVProgressHUD.setDefaultStyle(.dark)
        
        loadData()
        configureBarButton()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        loadData()
        tableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func loadData() {
        
        // Load my storaged credit cards
        let repo = CreditCardRepository()
        myCards = repo.All().filter({$0.cardNumber == $0.cardNumber})
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 2
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        switch section {
        case 0:
            return Localize.PayTo.rawValue.localized
        case 1:
            return Localize.PayWith.rawValue.localized
        default:
            return ""
        }
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0:
            return usersSelected.count
        case 1:
            return myCards.count + 1
        default:
            return 0
        }
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            return generateSelectedUserCell(tableView, index: indexPath)
        }
        
        if indexPath.section == 1 && indexPath.row == myCards.count {
            return generateNewCardCell(tableView, index: indexPath)
        }
        
        return generateCardInfoCell(tableView, index: indexPath)
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row == myCards.count {
            performSegue(withIdentifier: "newCardFromTransferSegue", sender: nil)
        } else {
            if myCards[indexPath.row].usingToPay {
                myCards[indexPath.row].usingToPay = false
                tableView.deselectRow(at: indexPath, animated: true)
            } else {
                myCards[indexPath.row].usingToPay = true
            }
        }
        
        navigationItem.rightBarButtonItem?.isEnabled = myCards.filter({ $0.usingToPay }).count > 0
    }
    
    override func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        if indexPath.row != myCards.count {
            myCards[indexPath.row].usingToPay = false
        }
        
        navigationItem.rightBarButtonItem?.isEnabled = myCards.filter({ $0.usingToPay }).count > 0
    }
    
    // MARK: - Table view cell methods
    func generateSelectedUserCell(_ tableView: UITableView, index: IndexPath) -> ConcludeTransferCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "sendMoneyCell", for: index) as! ConcludeTransferCell
        
        cell.fillCell(user: usersSelected[index.row])
        
        return cell
    }
    
    func generateCardInfoCell(_ tableView: UITableView, index: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "myCardCell", for: index)
        
        let cardNumber = myCards[index.row].cardNumber
        cell.textLabel?.text = "\(Localize.CardNumberEncripted.rawValue.localized)  ...\(cardNumber.substring(from: cardNumber.index(cardNumber.endIndex, offsetBy: -4)))"
        
        return cell
    }
    
    func generateNewCardCell(_ tableView: UITableView, index: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "myCardCell", for: index)
        cell.textLabel?.text = Localize.AddNewCard.rawValue.localized
        if index.row == myCards.count {
            cell.accessoryType = .disclosureIndicator
        }
        
        return cell
    }
    
    // MARK: - NavigationBar Methods
    func configureBarButton(comeToLoading: Bool = false) {
        let rightButton = UIBarButtonItem.init(title: Localize.Done.rawValue.localized, style: .done, target: self, action: #selector(finishTransfer))
        rightButton.isEnabled = myCards.filter({ $0.usingToPay }).count > 0
        navigationItem.setRightBarButtonItems([rightButton], animated: true)
    }
    
    func finishTransfer(_ sender: UIBarButtonItem) {
        view.endEditing(true)
        
        if let tf = self.tableView.viewWithTag(usersSelected[0].id) as? UITextField {
            if !(tf.text?.isEmpty)! {
                valueToSent = Double((tf.text?.replacingOccurrences(of: ",", with: "."))!)!
            }
        }
        
        if (valueToSent == 0.0) {
            displayAlert(title: "Ops", message: Localize.PutValue.rawValue.localized)
            return;
        }
        
        var validInputs = true
        
        if myCards.filter({ $0.usingToPay }).count == 0 {
            validInputs = false
        }
        
        if !validInputs {
            displayAlert(title: "Ops", message: Localize.RequiredFields.rawValue.localized)
            return;
        }
        
        SVProgressHUD.show()
        
        let wsTransaction = TransactionDto()
        let transaction = Transaction()
        transaction.destinationUserId = usersSelected[0].id
        
        let card = myCards.filter({ $0.usingToPay }).first
        if card == nil {
            SVProgressHUD.dismiss()
            displayAlert(title: "Ops", message: Localize.RequiredFields.rawValue.localized)
            return
        }
        
        transaction.cardNumber = (card?.cardNumber)!
        transaction.expiryDate = (card?.expiryDate)!
        transaction.cvv = (card?.cvv)!
        transaction.valueSent = valueToSent
        
        wsTransaction.postTransaction(transaction, completion: { success in
            wsTransaction.saveToDatabase(transaction)

            SVProgressHUD.dismiss()
            self.displayAlert(title: "", message: "🎉 \(Localize.TransactionSentSuccess.rawValue.localized)", returnToHome: true)
        }, fail: { message in
            SVProgressHUD.dismiss()
            self.displayAlert(title: "Ops", message: Localize.PostError.rawValue.localized)
        })
        
        
    }
    
    // MARK: - UIAlertview methods
    func displayAlert(title: String, message: String, returnToHome: Bool = false) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { action in
            if returnToHome {
                self.navigationController?.popToRootViewController(animated: true)
            }
        }))
        self.present(alert, animated: true, completion: nil)
    }

}
