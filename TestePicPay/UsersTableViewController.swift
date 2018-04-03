//
//  UsersTableViewController.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 28/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit
import Alamofire

class UserCell: UITableViewCell {
    @IBOutlet weak var profilePic: UICircularImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var idLabel: UILabel!
    @IBOutlet weak var usernameLabel: UILabel!
}

class UsersTableViewController: UITableViewController {
    
    let sessionManager = Alamofire.SessionManager.default
    var users: [User]?
    var selectedUser: User?
    var selectedUserImage: UIImage?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        getUsers()
    }
    
    func getUsers(){
        sessionManager.request(RestApi.getUsers()).responseJSON { [weak self] response in
            guard let strongSelf = self else { return }
            if let status = response.response?.statusCode {
                if status == 200 {
                    let decoder = JSONDecoder()
                    do{
                        strongSelf.users = try decoder.decode([User].self, from: response.data!)
                        strongSelf.tableView.reloadData()
                    } catch {
                        print(error)
                    }
                } else {
                    //error
                }
            }
        }
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return users?.count ?? 0
    }

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedUser = users?[indexPath.row]
        let cell =  tableView.cellForRow(at: indexPath) as! UserCell
        selectedUserImage = cell.profilePic.image
        performSegue(withIdentifier: "makePayment", sender: self)
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "userCell", for: indexPath) as! UserCell
        if let user = users?[indexPath.row] {
            setup(cell, at: indexPath, with: user)
        }
        return cell
    }
    
    func setup(_ cell: UserCell, at indexPath: IndexPath, with user: User) {
        let imageUrl = URL(string: user.imagePath!)
        DispatchQueue.global().async {
            let data = try? Data(contentsOf: imageUrl!)
            DispatchQueue.main.async {
                cell.profilePic.image = UIImage(data: data!)
                cell.profilePic.layer.borderWidth = 1.0
            }
        }
        cell.nameLabel.text = user.name
        cell.idLabel.text = String(format: "id_label".localized, user.id!)
        cell.usernameLabel.text = user.username
    }

    
    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "makePayment" {
            let nextVC = segue.destination as! PaymentViewController
            nextVC.recipientUser = selectedUser
            nextVC.image = selectedUserImage
        }
    }
}
