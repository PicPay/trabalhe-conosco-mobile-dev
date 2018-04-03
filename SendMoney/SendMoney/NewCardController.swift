//
//  NewCardController.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 29/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import UIKit

class NewCardController: UITableViewController {

    var cardInfoDataSource: [CardInfo] = [CardInfo]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        hideKeyboardWhenTappedAround()
        setupViewResizerOnKeyboardShown()
        
        loadData()
        configureBarButton()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return Localize.CardInfo.rawValue.localized
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return cardInfoDataSource.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cardInfoCell", for: indexPath) as! CardInfoCell
        
        cell.fillCell(card: cardInfoDataSource[indexPath.row], index: indexPath.row)
        
        return cell
    }
    
    // MARK: - Load data
    func loadData() {
        cardInfoDataSource.append(contentsOf: CardInfo.getItemCard())
    }
    
    // MARK: - Text field Methods
    @IBAction func textFieldEditingEnd(_ sender: UITextField) {
        cardInfoDataSource[sender.tag].valueCardInfo = sender.text!
    }
    
    func setupViewResizerOnKeyboardShown() {
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(UIViewController.keyboardWillShowForResizing),
                                               name: Notification.Name.UIKeyboardWillShow,
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(UIViewController.keyboardWillHideForResizing),
                                               name: Notification.Name.UIKeyboardWillHide,
                                               object: nil)
    }
    
    // MARK: - Navigation bar methods
    func configureBarButton() {
        let rightButton = UIBarButtonItem.init(barButtonSystemItem: .save, target: self, action: #selector(saveNewCard))
        navigationItem.setRightBarButtonItems([rightButton], animated: true)
    }
    
    func saveNewCard() {
        view.endEditing(true)
        var validTexts = true
        for card in cardInfoDataSource {
            if card.valueCardInfo.isEmpty {
                validTexts = false
            }
        }
        
        if !validTexts {
            displayAlert(title: "Ops", message: Localize.RequiredFields.rawValue.localized)
            return;
        }
        
        let newCard = CreditCard()
        newCard.cardNumber = cardInfoDataSource[0].valueCardInfo
        newCard.expiryDate = cardInfoDataSource[1].valueCardInfo
        newCard.cvv = Int(cardInfoDataSource[2].valueCardInfo)!
        
        let repo = CreditCardRepository()
        repo.Add(item: newCard)
        
        displayAlert(title: "", message: "🎉 \(Localize.NewCardSuccess.rawValue.localized)", back: true)
    }
    
    // MARK: - UIAlertview methods
    func displayAlert(title: String, message: String, back: Bool = false) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { action in
            if back {
                self.navigationController?.popViewController(animated: true)
            }
        }))

        self.present(alert, animated: true, completion: nil)
    }

}
