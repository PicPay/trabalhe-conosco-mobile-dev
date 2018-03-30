//
//  PaymentViewController.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 29/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

class PaymentViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var profilePic: UICircularImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var idLabel: UILabel!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var value: UITextField!
    @IBOutlet weak var payButton: UIRoundedButton!
    @IBOutlet weak var noCardView: UIView!
    @IBOutlet weak var cardToBeUsedView: UIView!
    @IBOutlet weak var cardToBeUsedLabel: UILabel!
    var recipientUser: User?
    var image: UIImage?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        profilePic.image = image
        value.addTarget(self, action: #selector(inputDidChange), for: .editingChanged)
        value.delegate = self
    }
    
    func hideCardViews(){
        noCardView.alpha = 0
        cardToBeUsedView.alpha = 0
    }
    
    override func viewWillAppear(_ animated: Bool) {
        hideCardViews()
        showRelevantCardView()
    }
    
    @objc func inputDidChange() {
        if let amount = value.text {
            let formattedAmount = amount.currencyInputFormatting()
            value.text = formattedAmount
        }
    }
    
    func showRelevantCardView() {
        if let card = getCardToShow(){
            //show card to be used view
            cardToBeUsedView.alpha = 1
            cardToBeUsedLabel.text = String(format: "card_to_be_used".localized, card.last4Digits!)
        } else {
            //show no card view
            noCardView.alpha = 1
        }
    }
    
    func getCardToShow() -> CreditCard? {
        let database = DBUtil.shared
        let cards = database.listCards()
        if cards.isEmpty {
            return nil
        }
        let defaults = UserDefaults.standard
        let cardId = defaults.integer(forKey: Const.lastUsedCardId)
        if cardId != 0 { //if there was a last used card id saved in defaults
            return cards.first(where:{$0.id == cardId})
        } else {
            return cards[0]
        }
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        let currentCharacterCount = textField.text?.count ?? 0
        if (range.length + range.location > currentCharacterCount){
            return false
        }
        let newLength = currentCharacterCount + string.count - range.length
        return newLength <= 11
    }

    @IBAction func noCardViewTapped(_ sender: Any) {
        performSegue(withIdentifier: "addCard", sender: sender)
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
