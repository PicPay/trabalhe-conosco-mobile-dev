//
//  TransactionView.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class TransactionView: UIView {
    
    // MARK: - Properties
    static let storyboardId = "TransactionView"
    fileprivate var placeholderLabel = UILabel()
    fileprivate let txtMessagePlaceholder = "Mande uma mensagem legal aqui :)"
    fileprivate let txtValuePlaceholder = "R$0,00"
    
    // MARK: - Outlets
    @IBOutlet weak var imgAvatar: RoundedUIImage!
    @IBOutlet weak var lblUsername: UILabel!
    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var txtValue: UITextField!
    @IBOutlet weak var txtMessage: UITextView!
}

// MARK: - Functions

extension TransactionView {
    
    public func update(_ user: User, _ accountStore: AccountStore, _ delegate: CredcardPaymentViewProtocol) {
        setUpPaymentView(delegate: delegate, accountStore: accountStore)
        
        lblUsername.text = user.username
        lblName.text = user.name
        txtValue.placeholder = txtValuePlaceholder
        txtMessage.text = ""
        applyPlaceholder(on: txtMessage)
        
        if let url = URL(string: user.img) {
            ImageStore.shared.fetchImage(for: user.username, from: url) { [weak self] result in
                guard let wSelf = self else { return }
                
                switch result {
                case let .success(img):
                    wSelf.imgAvatar.image = img
                case let .failure(error):
                    dd(error)
                }
            }
        }
    }
    
    fileprivate func setUpPaymentView(delegate: CredcardPaymentViewProtocol, accountStore: AccountStore) {
        let paymentView: CredcardPaymentView = .fromNib()
        paymentView.delegate = delegate
        paymentView.accountStore = accountStore
        
        txtValue.inputAccessoryView = paymentView
        txtMessage.inputAccessoryView = paymentView
    }
    
    // TextView Placeholder
    
    fileprivate func applyPlaceholder(on textView: UITextView) {
        guard let font = textView.font else { return }
        
        let pointSize = font.pointSize
        placeholderLabel.text = txtMessagePlaceholder
        placeholderLabel.font = UIFont.systemFont(ofSize: pointSize)
        placeholderLabel.sizeToFit()
        textView.addSubview(placeholderLabel)
        placeholderLabel.frame.origin = CGPoint(x: 5, y: pointSize / 2)
        placeholderLabel.textColor = UIColor.lightGray
        placeholderLabel.isHidden = !textView.text.isEmpty
    }
    
    public func textViewDidChange(_ textView: UITextView) {
        placeholderLabel.isHidden = !textView.text.isEmpty
    }
}
