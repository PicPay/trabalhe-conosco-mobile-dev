//
//  CredcardCoordinator.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class CredcardListCoordinator: Coordinator {
    fileprivate var credcardCreateCoordinator: CredcardCreateCoordinator?
    fileprivate let presenter: UINavigationController
    fileprivate var accountStore: AccountStore
    
    init(presenter: UINavigationController, account: AccountStore) {
        self.presenter = presenter
        self.accountStore = account
    }
    
    public func start() {
        let credcardViewController = CredcardListViewController(nibName: nil, bundle: nil)
        credcardViewController.title = "Formas de pagamento"
        credcardViewController.accountStore = accountStore
        credcardViewController.delegate = self
        
        presenter.pushViewController(credcardViewController, animated: true)
    }
}

// MARK: - Delegates

extension CredcardListCoordinator: CredcardListViewControllerProtocol {
    public func goesToCredcardCreate() {
        credcardCreateCoordinator = CredcardCreateCoordinator(presenter: presenter, account: accountStore)
        credcardCreateCoordinator?.start()
    }
    
    public func goesToTransaction() {
        presenter.popToRootViewController(animated: true)
    }
}
