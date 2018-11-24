//
//  UsersViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

// MARK: - Overrides

public final class UsersViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "UsersViewController"

    // MARK: - Computed
    fileprivate var mainView: UsersView {
        guard let _view = view as? UsersView else { preconditionFailure("Please, create the mainview to manager outlets.") }

        return _view
    }
    
    override public func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override public func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        setUp()
    }
}

// MARK: - IBActions

extension UsersViewController {
    // Make your IBActions here
}

// MARK: - Functions

extension UsersViewController {
    fileprivate func setUp() {
    }
}

// MARK: - Delegates
