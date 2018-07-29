//
//  CustomAlertController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class CustomAlertController {
    
    static func showCustomAlert(_ title: String, message: String, delegate: UIViewController, cancelAction:Bool = false, handler: (()->Void)?){
        let alertController = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { (alert) in
            if let handler = handler{
                handler()
            }            
        }))
        
        if(cancelAction){
            alertController.addAction(UIAlertAction(title: "Cancelar", style: UIAlertActionStyle.destructive, handler: nil))
        }
        
        delegate.present(alertController, animated: true, completion: nil)
    }
}
