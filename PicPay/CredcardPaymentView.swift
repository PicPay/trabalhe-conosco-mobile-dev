//
//  CredcardPaymentView.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public protocol CredcardPaymentViewProtocol: class {
    func btnPaymentDidTapped(_ sender: UIButton)
    func btnMethodDidTapped(_ sender: UIButton)
}

public final class CredcardPaymentView: UIView {
    
    // MARK: - Properties
    static let storyboardId = "CredcardPaymentView"
    public weak var delegate: CredcardPaymentViewProtocol?
    public var accountStorage: AccountStorage?

    // MARK: - Outlets
    @IBOutlet weak var lblMethod: UILabel!
    @IBOutlet weak var lblMethodTitle: UILabel!
    @IBOutlet weak var btnPayment: RoundedUIButton!
    @IBOutlet weak var btnMethod: UIButton!
    
    public override func layoutSubviews() {
        setUp()
    }
}

// MARK: - IBActions

extension CredcardPaymentView {
    @IBAction func btnPaymentDidTapped(_ sender: UIButton) {
        guard let delegate = delegate else { return }
        
        delegate.btnPaymentDidTapped(sender)
    }
    
    @IBAction func btnMethodDidTapped(_ sender: UIButton) {
        guard let delegate = delegate else { return }
        
        delegate.btnMethodDidTapped(sender)
    }
}

// MARK: - Functions

extension CredcardPaymentView {
    public func setUp() {
        guard let accountStorage = accountStorage else { return }
        
        lblMethod.text = "Forma de pagamento"
        lblMethodTitle.text = "Nenhum cartão selecionado"
        btnPayment.setTitle("Pagar", for: .normal)
        btnMethod.setTitle("", for: .normal)
        if let card = accountStorage.account.activeCard {
            lblMethodTitle.text = "\(card.number)"
        }
    }
}
