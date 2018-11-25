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
    let accountStore: AccountStore
    
    init(window: UIWindow) {
        self.window = window
        self.accountStore = AccountStore()
        rootViewController = UINavigationController()
        rootViewController.navigationBar.isUserInteractionEnabled = false
        usersCoordinator = UsersCoordinator(presenter: rootViewController, account: accountStore)
    }
    
    func start() {
        window.rootViewController = rootViewController
        usersCoordinator.start()
        window.makeKeyAndVisible()
    }
}
