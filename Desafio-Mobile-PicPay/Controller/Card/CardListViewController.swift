//
//  CardListViewController.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 26/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit
import CoreData

class CardListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, NSFetchedResultsControllerDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var registerButton: UIButton!    
    let cellID = "cellID"
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var fetchedResultsController:NSFetchedResultsController<Card>!
    var selectedIndex = IndexPath(item: 0, section: 0)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpTableView()
        setUpFetchResultController()
        getCardListIndex()
    }
    
    func setUpTableView(){
        tableView.tableFooterView = UIView()
        tableView.delegate = self
        tableView.dataSource = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.isNavigationBarHidden = false
        self.tableView.reloadData()
    }

    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return fetchedResultsController.sections?[section].numberOfObjects == 0 ? 50 : 0
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return fetchedResultsController.sections?[section].numberOfObjects ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: cellID, for: indexPath) as! CardTableViewCell
        cell.card = fetchedResultsController.object(at: indexPath)
        if let seletecObjectID = UserDefaults.standard.object(forKey: "selectedCardID") as? String {
            if (cell.card?.url_string_id == seletecObjectID){
                cell.accessoryType = .checkmark
            }
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let selectedCell = self.tableView.cellForRow(at: selectedIndex)
        selectedCell?.accessoryType = .none
        let cell = self.tableView.cellForRow(at: indexPath)
        cell?.selectionStyle = .none
        cell?.accessoryType = .checkmark
        selectedIndex = indexPath
        UserDefaults.standard.set(fetchedResultsController.object(at: indexPath).url_string_id, forKey: "selectedCardID")
        NotificationCenter.default.post(name: PaymentViewController.updatePaymentTypeNotificationName, object: self)
        UserDefaults.standard.set(selectedIndex.item, forKey: "selectedCardIndex")
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = Bundle.main.loadNibNamed("EmptyCardsHeadeCell", owner: self, options: nil)?.first as! EmptyCardsHeaderCell
        return headerView
    }
    
    @IBAction func didPressRegisterBUtton(_ sender: Any) {
        performSegue(withIdentifier: Strings.goToCardViewController, sender: nil)
    }
    
    @IBAction func didPressCloseBUtton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func getCardListIndex(){
        selectedIndex = IndexPath(item: UserDefaults.standard.integer(forKey: "selectedCardIndex"), section: 0)
    }
}
