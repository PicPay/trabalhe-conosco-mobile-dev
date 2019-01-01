//
//  PaymentViewController.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class PaymentViewController: BaseTableViewCStatic {

    deinit {
        print("Removeu referência")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        let cerd = CredCoreData()
        let credcard = cerd.FetchRequest()
        if credcard?.numero != nil {
            let numero = credcard!.numero!
            let final = numero.suffix(4)
            self.payment.credCard.text = "MasterCard " + final
        }
        ConfigureNavTitle()
    }
    
    
    override func viewWillDisappear(_ animated: Bool) {
        
        self.navibar.removeFromSuperview()
        //Replace the color of next viewcontroller and change he's color to green
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: nil, action: nil)
        navigationItem.backBarButtonItem?.tintColor = .lightGreen
    }
    
    
    //Constants
    var contato: Contato!

    
    let paymentView: ViewButton = {
        let teste = ViewButton()
        teste.button.isUserInteractionEnabled = false
        teste.button.titleLabel?.font = UIFont.boldSystemFont(ofSize: 18)
        teste.button.isHidden = false
        teste.button.backgroundColor = .lightGray
        teste.button.setTitle("Pagar", for: .normal)
        teste.button.addTarget(self, action: #selector(goToChecking(_:)), for: .touchUpInside)
        return teste
    }()
    
    
    let navibar = NaviBarCustom()
    let payment = PaymentView()
    var delegate: CheckViewControllerProtocol!
    override func viewDidLoad() {
        super.viewDidLoad()
        configurePaymentView()
    }
    
    
    //MARK:- PaymentViewController configuration
    fileprivate func configurePaymentView() {
        self.view.backgroundColor = .strongBlack
        self.payment.valuePayment.delegate = self
        self.payment.editCredcard.addTarget(self, action: #selector(EditController), for: .touchUpInside)
    }
    

    //Configure Title Navigationbar
    fileprivate func ConfigureNavTitle() {
        guard let navigationBar = self.navigationController?.navigationBar else { return }
        navigationBar.addSubview(self.navibar)
        self.navibar.anchor(top: nil, leading: navigationBar.leadingAnchor, bottom: navigationBar.bottomAnchor, trailing: navigationBar.trailingAnchor, padding: .init(), size: .init(width: 0, height: 60))
        self.navibar.contatoViewModel = self.contato.map( {return NavBarViewModel(contato: $0) })
    }
    
    
    //MARK:- Make the Paymeny
    @objc fileprivate func goToChecking(_ sender: DefaultButton) {
        self.paymentView.button.isUserInteractionEnabled = false
        self.paymentView.button.backgroundColor = .lightGray
        
        let cred = CredCoreData()
        let credcard = cred.FetchRequest()
        let credcardmodel = credcard.map( { return CredCardViewModel(credcard: $0) })
        let payment = self.payment.valuePayment.text!
        let pay = PaymentRequest(cred: credcardmodel!, destination: self.contato, payment: payment)
        
        pay.PaymentRequest { [weak self] (request: TicketUser) in
            if request.transaction.success {
                self?.didPaymentSuccess(request)
            } else {
                self?.didPaymentFailure(request)
            }
        }
    }
    
    
    //Call NewCredCardFormVC
    @objc fileprivate func EditController() {
        let viewc = NewCredCardFormVC()
        self.navigationController?.pushViewController(viewc, animated: true)
    }

    
    //Make the bottom tabbar
    override var canBecomeFirstResponder: Bool {
        return true
    }
    override var inputAccessoryView: UIView? {
        return self.paymentView
    }
 
    

}

extension PaymentViewController: UITextFieldDelegate {
    
    
    //MARK:- Payment request Protocols
    func didPaymentSuccess(_ ticketU: TicketUser) {
        let data = TicketDataSource()
        let _ = data.createTicketForFriend(use: ticketU)
        data.save()
        self.delegate.didPaymentSuccess(with: ticketU)
        self.navigationController?.popToRootViewController(animated: true)
    }
    
    func didPaymentFailure(_ ticketU: TicketUser) {
        self.paymentView.button.isUserInteractionEnabled = true
        self.paymentView.button.backgroundColor = .lightGreen
        self.changeButtonState(true)
    }
    
    
    
    //MARK:- Delegate Protocols
    func textFieldDidBeginEditing(_ textField: UITextField) {
        self.payment.valuePayment.changeColor()
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.payment.valuePayment.returnDefaultColor()
    }
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let str = textField.text! + string
        var strchg = str.removeCharacters()
        let num = strchg.count
        
        if string == "" && textField.text!.last == "0" {
            self.changeButtonState(false)
        }
        if string != "" {
            self.changeButtonState(true)
            if num > 3 {
                textField.text =  strchg.maskOnTypingPayment()
                return false
            } else {
                if strchg.first == "0" {
                    strchg.removeFirst()
                }
                let st = "0,0" + strchg
                textField.text = st
                return false
            }
        } else {
            textField.text = strchg.removeMaskPayment()
            if Int(strchg) == 0 {
                changeButtonState(false)
            }
            return false
        }
    }
    

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
    fileprivate func changeButtonState(_ state: Bool) {
        if state {
            self.paymentView.button.isUserInteractionEnabled = state
            self.paymentView.button.backgroundColor = .lightGreen
        } else {
            self.paymentView.button.isUserInteractionEnabled = state
            self.paymentView.button.backgroundColor = .lightGray
            
        }
    }
    
}
