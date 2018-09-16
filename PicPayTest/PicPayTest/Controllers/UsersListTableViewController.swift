//
//  UsersListTableViewController.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 12/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import UIKit
import Alamofire

class UsersListTableViewController: UITableViewController, UISearchResultsUpdating, UISearchControllerDelegate, UISearchBarDelegate {

    var searchController: UISearchController?
    var viewVazia: UIView?
    var users: [User] = []
    var arrayFiltrado: [User] = []
    var userSelecionado: User?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let nib = UINib(nibName: "ViewVazia", bundle: nil)
        self.viewVazia = nib.instantiate(withOwner: self, options: nil)[0] as? UIView
        
        self.tableView.tableFooterView = UIView()
        self.setupSearchBar()
        
        
        self.loadUsers()
    }
    
    func setupSearchBar() {
        searchController = UISearchController(searchResultsController: nil)
        self.searchController?.searchResultsUpdater = self
        self.searchController?.delegate = self
        self.searchController?.searchBar.delegate = self
        self.searchController?.searchBar.placeholder = "Pesquise por uma pessoa"
        self.searchController?.hidesNavigationBarDuringPresentation = true
        self.searchController?.dimsBackgroundDuringPresentation = false
        self.definesPresentationContext = true
        self.tableView.tableHeaderView = searchController?.searchBar
    }

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let search = self.searchController {
            if let text = search.searchBar.text {
                if search.isActive && !text.isEmpty {
                    return arrayFiltrado.count
                } else {
                    return users.count
                }
            }
        }
        return 0
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let search = searchController {
            if let cell = tableView.dequeueReusableCell(withIdentifier: "userCell", for: indexPath) as? UsersListTableViewCell {
                if search.isActive, let searchText = search.searchBar.text, !searchText.isEmpty {
                    cell.setupCell(user: arrayFiltrado[indexPath.row])
                } else {
                    cell.setupCell(user: users[indexPath.row])
                }
                return cell
            }
        }
        return UITableViewCell()
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let search = self.searchController {
            if search.isActive, let searchText = search.searchBar.text, !searchText.isEmpty {
                self.userSelecionado = self.arrayFiltrado[indexPath.row]
            } else {
                self.userSelecionado = self.users[indexPath.row]
            }
        }
        performSegue(withIdentifier: "paymentSegue", sender: self)
            
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "paymentSegue" {
            if let paymentController = segue.destination as? PaymentTableViewController {
               paymentController.userSelected = self.userSelecionado
            }
        }
    }
    
    func updateSearchResults(for searchController: UISearchController) {
        if let text = searchController.searchBar.text {
            if !text.isEmpty {
                self.arrayFiltrado = users.filter { (user) -> Bool in
                    user.name.uppercased().contains(text.uppercased()) ||
                    user.username.uppercased().contains(text.uppercased())
                }
            }
        }
        self.tableView.reloadData()
        self.addView(self.viewVazia!, tableView: self.tableView)
    }
    
    func addView(_ viewVazia: UIView, tableView: UITableView) {
        if self.tableView.numberOfRows(inSection: 0) == 0 {
            viewVazia.frame = tableView.bounds
            tableView.addSubview(viewVazia)
        } else {
            viewVazia.removeFromSuperview()
        }
    }
    
    override func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if let search = searchController {
            let y = scrollView.contentOffset.y
        
            if y < 0 {
                search.searchBar.transform = CGAffineTransform(translationX: 0, y: max(y, 0));
            }
        }
    }
}
extension UsersListTableViewController {
    func loadUsers() {
        Alamofire.request("http://careers.picpay.com/tests/mobdev/users").responseJSON { (response) in
            if let data = response.data {
                if let users = try? JSONDecoder().decode(Users.self, from: data) {
                    self.users = users
                    self.tableView.reloadData()
                }
            } else {
                let alert = UIAlertController(title: "Atenção", message: "Serviço Indisponível no momento", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Tentar Novamente", style: .default, handler: { (action) in
                    self.loadUsers()
                }))
                alert.addAction(UIAlertAction(title: "Cancelar", style: .default, handler: nil))
                self.present(alert, animated: true, completion: nil)
            }
        }
    }
}
