//
//  CredcardListView.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class CredcardListView: UIView {
    
    // MARK: - Properties
    static let storyboardId = "CredcardListView"

    // MARK: - Outlets
    @IBOutlet weak var tableView: UITableView! {
        didSet {
            tableView.register(CredcardListUIViewCell.instanceFromNib(), forCellReuseIdentifier: CredcardListUIViewCell.storyboardId)
            tableView.estimatedRowHeight = 70
            tableView.rowHeight = UITableView.automaticDimension
        }
    }

}

// MARK: - Functions

extension CredcardListView {
    // Make your functions here
}
