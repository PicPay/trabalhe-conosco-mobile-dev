//
//  ContactsTableViewController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class ContactsTableViewController: UITableViewController, UISearchBarDelegate {

    var contacts = [Contact]()
    let cellID = "cellID"
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    let searchController = UISearchController(searchResultsController: nil)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.tableFooterView = UIView()
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(image: #imageLiteral(resourceName: "list"), style: .done, target: self, action: #selector(showPaymentHistoryController))
        getContacts()
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return contacts.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: cellID, for: indexPath) as! ContactsTableViewCell
        cell.contact = contacts[indexPath.item]
        return cell
    }
    
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView()
        return view
    }
    
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 10
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: Strings.goToPaymentViewController, sender: contacts[indexPath.item])
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let paymentViewController = segue.destination as? PaymentViewController, let contact = sender as? Contact else {return}
        paymentViewController.contact = contact
    }
    
    @objc func showPaymentHistoryController(){
        let layout = UICollectionViewFlowLayout()
        let paymentHistoryCollectionViewController = PaymentHistoryCollectionViewController(collectionViewLayout: layout)        
        self.navigationController?.show(paymentHistoryCollectionViewController, sender: self)
    }
    
    func getContacts(){
        appDelegate.customActivityIndicator.showActivityIndicator()
        self.tableView.isUserInteractionEnabled = false
        appDelegate.apiClient.fetchUserContacts { (contacts, message) in
            if let contacts = contacts{
                self.contacts = contacts
                DispatchQueue.main.async {
                    self.tableView.isUserInteractionEnabled = true
                    self.appDelegate.customActivityIndicator.hideActivityIndicator()
                    self.tableView.reloadData()}
            }
        }
    }
}
