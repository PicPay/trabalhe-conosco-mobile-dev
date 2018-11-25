//
//  CredcardCreateCoordinator.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class CredcardCreateCoordinator: Coordinator {
    fileprivate let presenter: UINavigationController
    fileprivate var accountStore: AccountStore
    
    init(presenter: UINavigationController, account: AccountStore) {
        self.presenter = presenter
        self.accountStore = account
    }
    
    public func start() {
        let credcardViewController = CredcardCreateViewController(nibName: nil, bundle: nil)
        credcardViewController.delegate = self
        credcardViewController.title = "Novo cartão"
        credcardViewController.accountStore = accountStore
        
        presenter.pushViewController(credcardViewController, animated: true)
    }
}

extension CredcardCreateCoordinator: CredcardCreateViewControllerProtocol {
    public func goesToTransaction() {
        presenter.popToRootViewController(animated: true)
    }
}
