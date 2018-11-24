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
    
    init(presenter: UINavigationController) {
        self.presenter = presenter
    }
    
    public func start() {
        let usersViewController = UsersViewController(nibName: nil, bundle: nil)
//        usersViewController.delegate = self
        presenter.pushViewController(usersViewController, animated: true)
    }
}
