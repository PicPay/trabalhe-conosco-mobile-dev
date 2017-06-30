//
//  ViewController.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 26/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import UIKit
import SVProgressHUD

class MyTransfersController: UITableViewController {
    
    let repo: Repository = Repository()
    var transactions: [Transaction] = [Transaction]()
    var users: [UserProfile] = [UserProfile]()

    @IBOutlet var noContentView: UIView!
    @IBOutlet weak var noContentText: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = Localize.MyTransfers.rawValue.localized
        navigationItem.leftBarButtonItem?.title = Localize.MyCards.rawValue.localized
        
        SVProgressHUD.setDefaultStyle(.dark)
        
        
        setupScreen()
        loadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        loadData()
        tableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Setup screen
    func loadData() {
        SVProgressHUD.show()
        let repo = TransactionRepository()
        let results = repo.All()
        
        transactions = results.filter({$0.cardNumber == $0.cardNumber})
        
        let wsUser = UserDto()
        wsUser.getUsersApi(onSuccess: {(response, usersList) in
            self.users = usersList!
            self.tableView.reloadData()
            self.setupScreen()
            SVProgressHUD.dismiss()
        }, onFail: {(response, message) in
                print(message)
            SVProgressHUD.dismiss()
        })
    }
    
    func setupScreen() {
        noContentText.text = Localize.NoContentTransfer.rawValue.localized
        noContentView.frame = CGRect(x: 0, y: 0, width: 320.0, height: 640.0)
        let withoutContent = transactions.count == 0
        
        if withoutContent {
            noContentView.isHidden = false
            tableView.backgroundView = noContentView
        } else {
            noContentView.isHidden = true
            tableView.backgroundView = nil
        }
    }
    
    // MARK: - Table view delegate
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return transactions.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "myTrasnferCell", for: indexPath)
        let user = users.filter({ $0.id == transactions[indexPath.row].destinationUserId }).first
        
        cell.textLabel?.text = "\(Localize.YouPaid.rawValue.localized)\(user?.username ?? "")"
        //
        cell.detailTextLabel?.text = "\(Localize.Currency.rawValue.localized)\(String(format: "%.02f", locale: Locale.current, arguments: [transactions[indexPath.row].valueSent]))"
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.delete) {
            let repo = TransactionRepository()
            repo.Delete(item: transactions[indexPath.row])
            
            transactions.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
        }
    }
}
