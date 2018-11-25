//
//  UsersCoordinator.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class UsersCoordinator: Coordinator {
    fileprivate let presenter: UINavigationController
    fileprivate var usersViewController: UsersViewController?
    fileprivate var transactionCoordinator: TransactionCoordinator?
    fileprivate var accountStorage: AccountStorage
    
    init(presenter: UINavigationController, accountStorage: AccountStorage) {
        self.presenter = presenter
        self.accountStorage = accountStorage
    }
    
    public func start() {
        let usersViewController = UsersViewController(nibName: nil, bundle: nil)
        usersViewController.title = "Contatos"
        usersViewController.delegate = self
        presenter.pushViewController(usersViewController, animated: true)
    }
}

// MARK: - Delegates

extension UsersCoordinator: UsersViewControllerProtocol {
    public func goesToTransaction(to user: User) {
        transactionCoordinator = TransactionCoordinator(presenter: presenter, user: user, accountStorage: accountStorage)
        transactionCoordinator?.start()
    }
}
