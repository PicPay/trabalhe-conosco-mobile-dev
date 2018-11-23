//
//  AlertHelper.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

class AlertHelper {
    
    struct Title {
        static let `default` = "Desafio PicPay"
        static let none = ""
        static let ops = "Ops..."
        static let proccessingError = "Ops...\nTivemos algum problema"
        static let deleteCard = "Excluir cartão"
    }
    
    struct Button {
        static let ok = "OK"
        static let confirm = "Confirmar"
        static let delete = "Excluir"
        static let cancel = "Cancelar"
        static let yes = "Sim"
        static let no = "Não"
        static let close = "Fechar"
        static let signout = "Sair"
        static let `try` = "Tentar"
        static let tryAgain = "Tentar Novamente"
        static let unlock = "Desbloquear"
        static let resend = "Reenviar"
    }
    
    struct Messages {
    }
    
    static let titleAlertFont = [NSAttributedString.Key.font: UIFont(name: helveticaNeueMedium, size: 18.0)!]
    static let descriptionAlertFont = [NSAttributedString.Key.font: UIFont(name: helveticaNeueMedium, size: 14.0)!]
    
    static func showAlert(title: String,
                          message: String,
                          attributes: [NSAttributedString.Key : Any]? = descriptionAlertFont,
                          viewController: UIViewController,
                          imageTitle: UIImageView? = nil,
                          confirmButtonText: String = "OK",
                          actionHandler: ((_ action:UIAlertAction) -> Void)? = nil) {
        let alertController = UIAlertController(title: title, message: message, preferredStyle:.alert)
        alertController.view.tintColor = greenColor
        
        let titleAttrString = NSMutableAttributedString(string: title, attributes: titleAlertFont)
        alertController.setValue(titleAttrString, forKey: "attributedTitle")
        
        let messageAttrString = NSMutableAttributedString(string: message, attributes: attributes)
        alertController.setValue(messageAttrString, forKey: "attributedMessage")
        
        let confirmAction = UIAlertAction(title: confirmButtonText, style: UIAlertAction.Style.default , handler: actionHandler)
        
        alertController.addAction(confirmAction)
        
        if imageTitle != nil {
            imageTitle?.frame = CGRect(x: alertController.view.bounds.origin.x, y: alertController.view.bounds.origin.y,
                                       width: alertController.view.bounds.width, height: (imageTitle?.bounds.height)!)
            imageTitle?.contentMode = .scaleAspectFit
            imageTitle?.autoresizingMask = [.flexibleWidth]
            
            let newTitle = "\n\n" + title
            alertController.title = newTitle
            imageTitle?.center = CGPoint(x: alertController.view.bounds.width / 2, y: 35)
            alertController.view.addSubview(imageTitle!)
        }
        
        viewController.present(alertController, animated: true) {
            alertController.view.tintColor = greenColor
        }
    }
    
    static func showDialogAlert(title: String,
                                message: String,
                                attributes: [NSAttributedString.Key : Any]? = descriptionAlertFont,
                                viewController: UIViewController,
                                imageTitle: UIImageView? = nil,
                                leftTopButtonText: String = NSLocalizedString("Cancel", comment: ""),
                                rightBottomButtonText: String = NSLocalizedString("Continue", comment: ""),
                                leftButtonHandler: ((_ action:UIAlertAction) -> Void)? = nil,
                                rightButtonHandler: ((_ action:UIAlertAction) -> Void)?) {
        let alertController = UIAlertController(title: title, message: message, preferredStyle:.alert)
        alertController.view.tintColor = greenColor
        
        let titleAttrString = NSMutableAttributedString(string: title, attributes: titleAlertFont)
        alertController.setValue(titleAttrString, forKey: "attributedTitle")
        
        let messageAttrString = NSMutableAttributedString(string: message, attributes: attributes)
        alertController.setValue(messageAttrString, forKey: "attributedMessage")
        
        let leftTopAction = UIAlertAction(title: leftTopButtonText, style: UIAlertAction.Style.default, handler: leftButtonHandler)
        let rightBottomAction = UIAlertAction(title: rightBottomButtonText, style: UIAlertAction.Style.default , handler: rightButtonHandler)
        
        alertController.addAction(leftTopAction)
        alertController.addAction(rightBottomAction)
        
        if imageTitle != nil {
            imageTitle?.frame = CGRect(x: alertController.view.bounds.origin.x, y: alertController.view.bounds.origin.y,
                                       width: alertController.view.bounds.width, height: (imageTitle?.bounds.height)!)
            imageTitle?.contentMode = .scaleAspectFit
            imageTitle?.autoresizingMask = [.flexibleWidth]
            
            let newTitle = "\n\n" + title
            alertController.title = newTitle
            imageTitle?.center = CGPoint(x: alertController.view.bounds.width / 2, y: 35)
            alertController.view.addSubview(imageTitle!)
        }
        
        viewController.present(alertController, animated: true) {
            alertController.view.tintColor = greenColor
        }
    }
    
    
}
