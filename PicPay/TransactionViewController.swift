//
//  TransactionViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

// MARK: - Overrides

public final class TransactionViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "TransactionViewController"
    public var user: User?
    public var accountStore: AccountStore?

    // MARK: - Computed
    fileprivate var mainView: TransactionView {
        guard let _view = view as? TransactionView else { preconditionFailure("Please, create the mainview to manager outlets.") }

        return _view
    }
    
    override public func viewDidLoad() {
        super.viewDidLoad()
        
        setUp()
    }
    
    override public func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }
}

// MARK: - IBActions

extension TransactionViewController {
    @IBAction func dimiss(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
}

// MARK: - Functions

extension TransactionViewController {
    fileprivate func setUp() {
        guard let user = user else { dd("There's no user"); return }
        guard let accountStore = accountStore else { dd("Account store not loaded"); return }
        
        mainView.update(user, accountStore, self)
    }
    
}

// MARK: - Delegates

extension TransactionViewController: UITextViewDelegate {
    public func textViewDidChange(_ textView: UITextView) {
        mainView.textViewDidChange(textView)
    }
}

extension TransactionViewController: CredcardPaymentViewProtocol {
    public func btnPaymentDidTapped(_ sender: UIButton) {
        dd("Send payment")
    }
    
    public func btnMethodDidTapped(_ sender: UIButton) {
        dd("goes to cards list")
    }
}
