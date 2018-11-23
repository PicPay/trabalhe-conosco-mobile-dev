//
//  UsersTableViewController.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

class UsersTableViewController: UITableViewController {
    
    let userIdentifier = "userIdentifier"
    var users = [User]()
    let segueToValue = "SegueToValue"
    
    private lazy var myRefreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.attributedTitle = NSAttributedString(string: "Puxe para atualizar.")
        refreshControl.backgroundColor = UIColor.clear
        refreshControl.addTarget(self, action: #selector(loadUsers), for: .valueChanged)

        return refreshControl
    }()
    
    // MARK: - View controller life cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.refreshControl = myRefreshControl
        loadUsers()
    }

    // MARK: - Functions
    
    @objc func loadUsers() {
        let interactor = UserInteractor()
        interactor.list(success: { (users) in
            self.users = users
            DispatchQueue.main.async {
                self.refreshControl?.endRefreshing()
                self.tableView.reloadData()
            }
        }) { (error) in
            DispatchQueue.main.async {
                self.refreshControl?.endRefreshing()
            }
            AlertHelper.showAlert(title: AlertHelper.Title.default,
                                  message: error.localizedDescription,
                                  viewController: self)
        }
    }
    
    // MARK: - Table view data source

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return users.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: userIdentifier, for: indexPath) as! UserTableViewCell

        let user = users[indexPath.row]
        cell.name.text = user.name
        
        let url = URL(string: user.img ?? "")
        cell.photo.kf.setImage(with: url)

        return cell
    }

    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 60
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let user = users[indexPath.row]
        self.performSegue(withIdentifier: segueToValue, sender: user)
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == segueToValue {
            if let destination = segue.destination as? UINavigationController {
                if let valueController = destination.viewControllers.first as? ValueViewController {
                    valueController.user = sender as? User
                }
            }
        }
    }
    

}
