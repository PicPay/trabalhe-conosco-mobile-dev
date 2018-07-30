//
//  RegisterCreditCardPopUpViewController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class RegisterCreditCardPopUpViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.black.withAlphaComponent(0.6)
    }
    
    @IBAction func didPressCloseButton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func didPressRegisterButton(_ sender: Any) {
        let creditCardStoryBoard = UIStoryboard(name: "CreditCard", bundle: nil)
        guard let cardListNavController = creditCardStoryBoard.instantiateViewController(withIdentifier: "CardListNavigationController") as? UINavigationController else {return}
        self.dismiss(animated: false, completion: {
            guard let rootViewController = UIApplication.shared.keyWindow?.rootViewController as? UINavigationController else {return}
            rootViewController.topViewController?.present(cardListNavController, animated: true, completion: nil)
        })
    }
}
