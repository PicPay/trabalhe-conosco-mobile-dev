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
    var recipientUser: User?
    var image: UIImage?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        profilePic.image = image
        value.addTarget(self, action: #selector(inputDidChange), for: .editingChanged)
        value.delegate = self
    }
    
    @objc func inputDidChange() {
        if let amount = value.text {
            let formattedAmount = amount.currencyInputFormatting()
            value.text = formattedAmount
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
