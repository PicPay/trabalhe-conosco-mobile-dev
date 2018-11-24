//
//  UsersViewController.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public protocol UsersViewControllerProtocol: class {
    func goesToTransaction(to user: User)
}

// MARK: - Overrides

public final class UsersViewController: UIViewController {
    
    // MARK: - Properties
    
    static let storyboardId = "UsersViewController"
    public weak var delegate: UsersViewControllerProtocol?
    fileprivate var source: UserDataSource = UserDataSource()
    fileprivate var manager: UsersManager = UsersManager()
    fileprivate let search = UISearchController(searchResultsController: nil)

    // MARK: - Computed
    fileprivate var mainView: UsersView {
        guard let _view = view as? UsersView else { preconditionFailure("Please, create the mainview to manager outlets.") }

        return _view
    }
    
    override public func viewDidLoad() {
        super.viewDidLoad()
        bind()
        setUp()
        fetch()
    }
    
    override public func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }
}

// MARK: - IBActions

extension UsersViewController {
    // Make your IBActions here
}

// MARK: - Functions

extension UsersViewController {
    fileprivate func setUp() {

        if #available(iOS 11.0, *) {
            navigationController?.navigationBar.prefersLargeTitles = true
            navigationController?.navigationItem.largeTitleDisplayMode = .always
            //            UINavigationBar.appearance().largeTitleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.green]
            navigationItem.searchController = search
        }
        
        title = "Contatos"
        search.obscuresBackgroundDuringPresentation = false
        search.searchBar.placeholder = "Quem você quer pagar?"
        search.searchResultsUpdater = self
        mainView.tableView.dataSource = source
    }
    
    fileprivate func bind() {
        source.model.bind { [weak self] meals in
            guard let wSelf = self else { return }
            wSelf.mainView.tableView.reloadData()
        }
    }
    
    fileprivate func fetch() {
        manager.fetch { [weak self] result in
            guard let wSelf = self else { return }
            
            switch result {
            case let .success(users):
                wSelf.source.model.content = users
                wSelf.source.allUsers = users
            case let .failure(error):
                dd(error)
            }
        }
    }
    
    fileprivate func searchBarIsEmpty() -> Bool {
        return search.searchBar.text?.isEmpty ?? true
    }
    
    fileprivate func filterContentForSearchText(_ searchText: String, scope: String = "All") {
        guard !searchBarIsEmpty() else {
            clearSearch()
            return
        }
        
        source.model.content = source.allUsers.filter { $0.name.lowercased().contains(searchText.lowercased() )}
    }
    
    fileprivate func clearSearch() {
        source.model.content = source.allUsers
    }

}

// MARK: - Delegates

extension UsersViewController: UITableViewDelegate {
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // Prevent index out of set
        guard indexPath.row < source.model.content.count else { dd("Can't load item from model"); return }
        guard let delegate = delegate else { return }
        
        // Selected user
        let user = source.model.content[indexPath.row]
        delegate.goesToTransaction(to: user)
    }
}

extension UsersViewController: UISearchResultsUpdating {
    public func updateSearchResults(for searchController: UISearchController) {
        guard let text = searchController.searchBar.text else { return }
        
        filterContentForSearchText(text)
    }
}
