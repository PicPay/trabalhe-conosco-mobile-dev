//
//  CredcardCreateViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public protocol CredcardCreateViewControllerProtocol: class {
    func goesToTransaction()
}

// MARK: - Overrides

public final class CredcardCreateViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "CredcardCreateViewController"
    fileprivate let manager: CredcardManager = CredcardManager()
    public var accountStore: AccountStore?
    public weak var delegate: CredcardCreateViewControllerProtocol?

    // MARK: - Computed
    fileprivate var mainView: CredcardCreateView {
        guard let _view = view as? CredcardCreateView else { preconditionFailure("Please, create the mainview to manager outlets.") }

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

extension CredcardCreateViewController {
    @IBAction func btnRegisterDidTapped(_ sender: UIButton) {
        guard let delegate = delegate else { return }
        guard let accountStore = accountStore else { return }
        guard let cardNumber = mainView.txtCardNumber?.text else { return }
        guard let cardValidate = mainView.txtCardValidate?.text else { return }
        guard let cardCvv = mainView.txtCardCvv?.text else { return }
        
        let card = Card(
            number: Int(cardNumber) ?? 0,
            validate: cardValidate,
            cvv: Int(cardCvv) ?? 0,
            isMainCard: true)
        
        manager.register(card: card, with: accountStore) { [weak self] result in
            guard let wSelf = self else { return }
            
            switch result {
            case .success:
                let alert = UIAlertController(title: "Cartão registrado com sucesso!", message: "", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Fechar", style: .default, handler: { _ in delegate.goesToTransaction() }))
                wSelf.present(alert, animated: true, completion: nil)
            case let .failure(error):
                var msgError = error.localizedDescription
                if let error = error as? GenericError, case let .parse(msg) = error {
                    msgError = msg
                }
                
                let alert = UIAlertController(title: "Dados incorretos", message: msgError, preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Fechar", style: .default, handler: nil))
                wSelf.present(alert, animated: true, completion: nil)
            }
        }
    }
}

// MARK: - Functions

extension CredcardCreateViewController {
    fileprivate func setUp() {
    }
}

// MARK: - Delegates
