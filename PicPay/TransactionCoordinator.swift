//
//  TransactionCoordinator.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class TransactionCoordinator: Coordinator {
    fileprivate let presenter: UINavigationController
    fileprivate var transactionViewController: TransactionViewController?
    fileprivate var user: User?
    fileprivate var accountStore: AccountStore
    
    init(presenter: UINavigationController, user: User, account: AccountStore) {
        self.presenter = presenter
        self.user = user
        self.accountStore = account
    }
    
    public func start() {
        let transactionViewController = TransactionViewController(nibName: nil, bundle: nil)
        transactionViewController.user = user
        transactionViewController.accountStore = accountStore
        transactionViewController.modalPresentationStyle = .overFullScreen
//        usersViewController.delegate = self
//        presenter.pushViewController(transactionViewController, animated: true)
        presenter.present(transactionViewController, animated: true, completion: nil)
    }
}

// MARK: - Delegates

//extension TransactionCoordinator: UsersViewControllerProtocol {
//    public func goesToTransaction(to user: User) {
//        //
//    }
//}
