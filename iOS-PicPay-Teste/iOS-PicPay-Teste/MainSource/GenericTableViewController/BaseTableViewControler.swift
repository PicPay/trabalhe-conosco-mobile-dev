//
//  BaseTableView.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 10/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class BaseTableViewController<B: BaseCell<I>, I>: UIViewController, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate, UIScrollViewDelegate{
    
    let cellid = "id"
    var didSelect: (I) -> () = { _ in }

    var tableView: UITableView = {
        let table = UITableView(frame: CGRect(), style: .grouped)
        table.backgroundColor = .clear
        return table
    }()
    
    var items = [I]()
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.register(B.self, forCellReuseIdentifier: cellid)
        tableView.tableFooterView = UIView()
        tableView.separatorStyle = .none
        let rc = UIRefreshControl()
        rc.tintColor = .white
        rc.addTarget(self, action: #selector(handleRefresh), for: .valueChanged)
        tableView.refreshControl = rc
        
    }
    @objc func handleRefresh () {
        
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.items.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        return UISwipeActionsConfiguration()
    }
    func tableView(_ tableView: UITableView, leadingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        return UISwipeActionsConfiguration()
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            let cell = tableView.dequeueReusableCell(withIdentifier: cellid, for: indexPath) as! BaseCell<I>
            cell.item = self.items[indexPath.row]
            return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let item = items[indexPath.row]
        didSelect(item)
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 0
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return UIView()
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        return true
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        return true
    }
    func textFieldDidBeginEditing(_ textField: UITextField) {
        
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        
    }
}

//Base Cell
class BaseCell<I>: UITableViewCell {
    var item: I!
}
