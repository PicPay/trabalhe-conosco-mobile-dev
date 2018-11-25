//
//  CredcardListViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

// MARK: - Overrides

public final class CredcardListViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "CredcardListViewController"

    // MARK: - Computed
    fileprivate var mainView: CredcardListView {
        guard let _view = view as? CredcardListView else { preconditionFailure("Please, create the mainview to manager outlets.") }

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

extension CredcardListViewController {
    // Make your IBActions here
}

// MARK: - Functions

extension CredcardListViewController {
    fileprivate func setUp() {
    }
}

// MARK: - Delegates
