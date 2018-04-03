//
//  NewTransferController.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 27/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import UIKit
import SVProgressHUD

class NewTransferController: UITableViewController {
    
    var users = [UserProfile]()
    var wsUser = UserDto()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.title = Localize.NewTransfer.rawValue.localized

        tableView.rowHeight = UITableViewAutomaticDimension;
        tableView.estimatedRowHeight = 70.0
        
        SVProgressHUD.setDefaultStyle(.dark)
        
        loadUsers()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return users.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "userNewTransferCell", for: indexPath) as! NewTransferCell
        cell.fillCell(user: users[indexPath.row])
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return Localize.TitleSelectUsers.rawValue.localized
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        for user in users {
            user.selectedToPay = false
        }
        
        users[indexPath.row].selectedToPay = true
        
        performSegue(withIdentifier: "sendMoneySegue", sender: nil)
    }
    
    // MARK: - Load data
    func loadUsers() {
        SVProgressHUD.show()
        
        wsUser.getUsersApi(onSuccess: { (response, retorno) in
            self.users = retorno!
            self.tableView.reloadData()
            SVProgressHUD.dismiss()
        }, onFail: {(response, e) in
            print("Response: \(String(describing: response?.statusCode))")
            print("Error: \(e)")
            SVProgressHUD.dismiss()
        })
    }
    
    // MARK: - Segue Methods
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let conclude = segue.destination as! ConcludeTransferController
        
        let usersSelected = users.filter({ $0.selectedToPay == true })
        conclude.usersSelected = usersSelected
    }

}







