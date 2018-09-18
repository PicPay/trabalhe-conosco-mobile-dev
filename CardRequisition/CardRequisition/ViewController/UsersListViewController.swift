//
//  ViewController.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/17/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import UIKit

class UsersListViewController: UIViewController {
    @IBOutlet weak var usersTableView: UITableView! {
        didSet {
            self.usersTableView.delegate = self
            self.usersTableView.dataSource = self
        }
    }
    
    var users: [User] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        self.navigationItem.title = "User List"
        NotificationCenter.default.addObserver(self, selector: #selector(userImageDownloadCompleted(_:)), name: .userImageDownloadCompleted, object: nil)
        
        
        AlamofireService.getAllUsers { (success, users) in
            if success, let users = users {
                self.users = users
                self.usersTableView.reloadData()
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @objc private func userImageDownloadCompleted(_ notification: Notification) {
        guard let userInfo = notification.userInfo as? [String: Any], let userId = userInfo[NotificationKeys.userId] as? Int, let index = self.users.index(where: { (user) -> Bool in return user.id == userId }) else {
            return
        }
        let indexPath = IndexPath(row: index, section: 0)
        if let visibleRows = self.usersTableView.indexPathsForVisibleRows, visibleRows.contains(indexPath) {
            self.usersTableView.reloadRows(at: [indexPath], with: .none)
        }
    }
    
    
}

extension UsersListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let storyboard = UIStoryboard(name: StoryboardName.payment, bundle: nil)
        if let vc = storyboard.instantiateViewController(withIdentifier: ViewControllerName.cardSelection) as? CardSelectionViewController {
            vc.user = self.users[indexPath.row]
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}

extension UsersListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.users.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: TableViewCells.usersCell, for: indexPath)
        let user = self.users[indexPath.row]
        cell.textLabel?.text = user.name
        cell.detailTextLabel?.text = user.username
        if let image = user.image {
            cell.imageView?.image = image
        } else if user.needsToDownloadImage() {
            user.downloadImage()
            cell.imageView?.image = ImageConstants.profilePlaceholderImage
        }
        return cell
    }
}

