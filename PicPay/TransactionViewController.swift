//
//  TransactionViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public protocol TransactionViewControllerProtocol: class {
    func goesToCredcardList()
    func dismiss()
}

// MARK: - Overrides

public final class TransactionViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "TransactionViewController"
    public var user: User?
    fileprivate var manager: TransactionManager = TransactionManager()
    public var accountStore: AccountStore?
    public weak var delegate: TransactionViewControllerProtocol?

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
        guard let value = mainView.txtValue.text else { return }
        guard let user = user else { return }
        guard let card = accountStore?.account.activeCard else { return }
        
        let transaction = Transaction(
            value: Float(value) ?? 0,
            user: user,
            card: card)
        
        manager.process(payment: transaction) { [weak self] result in
            guard let wSelf = self else { return }
            
            switch result {
            case .success:
                let alert = UIAlertController(title: "A transação foi efetuada com sucesso!", message: "", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Fechar", style: .default, handler: { _ in wSelf.dismiss() }))
                wSelf.present(alert, animated: true, completion: nil)
            case let .failure(error):
                guard let error = error as? GenericError else { return }
                if case let .parse(msgError) = error {
                    let alert = UIAlertController(title: msgError, message: "", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Fechar", style: .default, handler: nil))
                    wSelf.present(alert, animated: true, completion: nil)
                }
            }
        }
    }
    
    public func btnMethodDidTapped(_ sender: UIButton) {
        goesToCredcardList()
    }
}

extension TransactionViewController: TransactionViewControllerProtocol {
    public func dismiss() {
        guard let delegate = delegate else { return }
        
        delegate.dismiss()
    }
    
    public func goesToCredcardList() {
        guard let delegate = delegate else { return }
        
        delegate.goesToCredcardList()
    }
}
