//
//  UsersView.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class UsersView: UIView {
    
    // MARK: - Properties
    static let storyboardId = "UsersView"
    public let activityIndicator = UIActivityIndicatorView(style: .gray)

    // MARK: - Outlets
    @IBOutlet weak var tableView: UITableView! {
        didSet {
            tableView.register(UsersUIViewCell.instanceFromNib(), forCellReuseIdentifier: UsersUIViewCell.storyboardId)
            tableView.estimatedRowHeight = 70
            tableView.rowHeight = UITableView.automaticDimension
        }
    }
    
    public override func awakeFromNib() {
        setUp()
    }
}

// MARK: - Functions

extension UsersView {
    fileprivate func setUp() {
        addSubview(activityIndicator)
        activityIndicator.frame = bounds
    }
}
