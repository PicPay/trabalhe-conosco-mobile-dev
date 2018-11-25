//
//  CredcardListViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public protocol CredcardListViewControllerProtocol: class {
    func goesToCredcardCreate()
    func goesToTransaction()
}

// MARK: - Overrides

public final class CredcardListViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "CredcardListViewController"
    public var accountStorage: AccountStorage?
    fileprivate var manager: CredcardManager = CredcardManager()
    public weak var delegate: CredcardListViewControllerProtocol?
    fileprivate var source: CredcardDataSource = CredcardDataSource()

    // MARK: - Computed
    fileprivate var mainView: CredcardListView {
        guard let _view = view as? CredcardListView else { preconditionFailure("Please, create the mainview to manager outlets.") }

        return _view
    }
    
    override public func viewDidLoad() {
        super.viewDidLoad()
        
        bind()
        fetch()
        setUp()
    }
    
    override public func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }
}

// MARK: - IBActions

extension CredcardListViewController {
    fileprivate func bind() {
        source.model.bind { [weak self] meals in
            guard let wSelf = self else { return }
            wSelf.mainView.tableView.reloadData()
        }
    }
    
    fileprivate func fetch() {
        guard let accountStorage = accountStorage else { return }
        
        source.model.content = accountStorage.account.cards
    }
}

// MARK: - Functions

extension CredcardListViewController {
    fileprivate func setUp() {
        mainView.tableView.dataSource = source
    }
    
    @objc
    fileprivate func goesToCredcardCreate() {
        guard let delegate = delegate else { return }
        
        delegate.goesToCredcardCreate()
    }
}

// MARK: - Delegates

extension CredcardListViewController: UITableViewDelegate {
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let accountStorage = accountStorage else { return }
        guard indexPath.row < source.model.content.count else { dd("Can't load item from model"); return }
        guard let delegate = delegate else { return }
        
        // Selected user
        let card = source.model.content[indexPath.row]
        manager.setAsMainCard(card: card, with: accountStorage)
        delegate.goesToTransaction()
    }
    
    public func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        let viewForFooter: CredcardListViewforFooter = .fromNib()
        viewForFooter.btnAddCard.addTarget(self, action: #selector(goesToCredcardCreate), for: .touchUpInside)
        
        return viewForFooter
    }
    
    public func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return 70
    }
}
