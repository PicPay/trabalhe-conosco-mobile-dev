//
//  AppCoordinator.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

class AppCoordinator: Coordinator {
    let window: UIWindow
    let rootViewController: UINavigationController
    let usersCoordinator: UsersCoordinator
    let accountStorage: AccountStorage
    
    init(window: UIWindow) {
        self.window = window
        self.accountStorage = AccountStorage()
        rootViewController = UINavigationController()
        rootViewController.navigationBar.isUserInteractionEnabled = false
        usersCoordinator = UsersCoordinator(presenter: rootViewController, accountStorage: accountStorage)
    }
    
    func start() {
        window.rootViewController = rootViewController
        usersCoordinator.start()
        window.makeKeyAndVisible()
    }
}
