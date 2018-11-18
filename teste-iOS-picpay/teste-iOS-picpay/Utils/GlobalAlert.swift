//
//  GlobalAlert.swift
//  ClimaX
//
//  Created by Bruno Lopes de Mello on 28/04/18.
//  Copyright © 2018 Bruno Lopes de Mello. All rights reserved.
//

import Foundation
import UIKit

struct GlobalAlert {
    let controller: UIViewController
    let msg: String
    let confirmButton: Bool
    let confirmAndCancelButton: Bool
    let isModal: Bool
    
    init(with controller: UIViewController, msg: String, confirmButton: Bool = true,
         confirmAndCancelButton: Bool = false, isModal: Bool = false) {
        self.controller = controller
        self.msg = msg
        self.confirmButton = confirmButton
        self.confirmAndCancelButton = confirmAndCancelButton
        self.isModal = isModal
    }
    
    func showAlert() {
        let alerta = UIAlertController(title: "Atenção", message: self.msg, preferredStyle: UIAlertController.Style.alert)
        let confirmAction = UIAlertAction(title: "ok", style: .default, handler: nil)
        alerta.addAction(confirmAction)
        self.controller.present(alerta, animated: true, completion: nil)
    }
    
    func showAlertAndReturn() {
        let alerta = UIAlertController(title: "Atenção", message: self.msg, preferredStyle: UIAlertController.Style.alert)
        if isModal {
            
            if self.confirmButton && !self.confirmAndCancelButton {
                alerta.addAction(UIAlertAction(title: "ok", style: .default, handler: { (action) in
                    self.controller.dismiss(animated: true, completion: nil)
                    
                }))
            } else {
                alerta.addAction(UIAlertAction(title: "ok", style: .default, handler: { (action) in
                    self.controller.dismiss(animated: true, completion: nil)
                }))
                alerta.addAction(UIAlertAction(title: "cancel", style: .cancel, handler: { (action) in
                }))
            }
            
            
        } else {
            
            if self.confirmButton && !self.confirmAndCancelButton {
                alerta.addAction(UIAlertAction(title: "ok", style: .default, handler: { (action) in
                    self.controller.navigationController?.popViewController(animated: true)
                }))
            } else {
                alerta.addAction(UIAlertAction(title: "ok", style: .default, handler: { (action) in
                    self.controller.navigationController?.popViewController(animated: true)
                }))
                alerta.addAction(UIAlertAction(title: "cancel", style: .cancel, handler: { (action) in
                }))
            }
            
        }
        self.controller.show(alerta, sender: nil)
    }
}
