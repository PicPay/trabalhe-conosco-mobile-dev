//
//  PaymentViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 13/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit
import AVFoundation

class PaymentViewController: UIViewController {

    private let numberFormatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.locale = Locale(identifier: "pt_BR")
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return formatter
    }()
    
    private var observer: Any!
    
    private var text = ""
    
    var paymentInfo: (to: Person, card: Card)?
    
    @IBOutlet var toolbar: UIToolbar!
    
    @IBOutlet var textField: UITextField!
    
    @IBOutlet var activityView: UIActivityIndicatorView!
    
    @IBAction func pay(_ sender: UIBarButtonItem) {
        
        guard let info = paymentInfo else {
            return
        }
        
        guard let text = textField.text else {
            return
        }
        
        let typedText = String(text[text.index(text.startIndex, offsetBy: 3)...])
        
        if Decimal(string: typedText, locale: numberFormatter.locale) == 0.0 {
            let animations = { self.textField!.transform = CGAffineTransform(scaleX: 1.2, y: 1.2) }
            let completion = { self.textField!.transform = .identity }
            
            UIView.animate(withDuration: 0.1, animations: animations) { _ in
                UIViewPropertyAnimator(duration: 0.4, dampingRatio: 0.5, animations: completion)
                    .startAnimation()
            }
        } else {
            activityView.startAnimating()
            
            let payment = Payment(
                receiverID: info.to.id,
                receiverName: info.to.name,
                cardNumber: info.card.number,
                cardExpires: info.card.expires,
                cardCode: info.card.securityCode,
                amount: String(typedText))
            
            _ = PicPayService().sendPayment(payment) { success in
                if success {
                    AudioServicesPlaySystemSoundWithCompletion(1004) {
                        self.dismiss(animated: true)
                    }
                } else {
                    DispatchQueue.main.async {
                        self.activityView.stopAnimating()
                    }
                    
                    let alertController = UIAlertController(title: nil, message: nil, preferredStyle: .alert)
                    alertController.message = "Verifique os dados do cartão e tente novamente."
                    alertController.title = "A transação não foi aprovada"
                    alertController.view.tintColor = self.view.tintColor
                    
                    alertController.addAction(UIAlertAction(title: "OK", style: .default) { _ in
                        self.dismiss(animated: true)
                    })
                    
                    self.showDetailViewController(alertController, sender: self)
                }
            }
            
            textField.resignFirstResponder()
        }
    }
    
    @IBAction func dismiss(_ sender: UITapGestureRecognizer) {
        dismiss(animated: true)
        
        textField.resignFirstResponder()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        textField.inputAccessoryView = toolbar
        textField.becomeFirstResponder()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        observer = NotificationCenter.default.addObserver(forName: .UIKeyboardWillShow,
                                                          object: nil,
                                                          queue: OperationQueue.main,
                                                          using: keyboardWillShow)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        
        NotificationCenter.default.removeObserver(observer)
    }
    
    func keyboardWillShow(notification: Notification) {
        let frame = notification.userInfo![UIKeyboardFrameEndUserInfoKey] as! NSValue
        let duration = notification.userInfo![UIKeyboardAnimationDurationUserInfoKey] as! NSNumber
        
        UIView.animate(withDuration: duration.doubleValue, delay: 0.0, options: .curveEaseOut, animations: {
            self.additionalSafeAreaInsets.bottom = self.view.convert(frame.cgRectValue, from: nil).height
            self.view.layoutIfNeeded()
        }, completion: nil)
    }
}

extension PaymentViewController: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard string == string.trimmingCharacters(in: CharacterSet.whitespaces) else {
            return false
        }
        
        guard string.rangeOfCharacter(from: CharacterSet.decimalDigits.inverted) == nil else {
            return false
        }
        
        if string.isEmpty {
            text = String(text.dropLast())
            
            if text.isEmpty {
                textField.text = "R$ 0,00"
            } else {
                textField.text = "R$ " + numberFormatter.string(from:
                    NSDecimalNumber(string: text).multiplying(by: 0.01))!
            }
        } else {
            text += string
            
            textField.text = "R$ " + numberFormatter.string(from:
                NSDecimalNumber(string: text).multiplying(by: 0.01))!
        }
        
        return false
    }
}

extension PaymentViewController: UIGestureRecognizerDelegate {
    func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return !activityView.isAnimating
    }
}
