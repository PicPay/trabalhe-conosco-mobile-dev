//
//  HistoryViewController.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 18/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import UIKit

private let reuseIdentifier = "cell"

class HistoryViewController: UITableViewController {

    fileprivate let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMMM dd EEEE"
        return formatter
    }()
    
    fileprivate let timeFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.locale = Locale(identifier: "pt_BR")
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        return formatter
    }()
    
    fileprivate let numberFormatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.locale = Locale(identifier: "pt_BR")
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return formatter
    }()
    
    fileprivate var history = [Date:[Transaction]]()
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        for tx in PicPayService.loadTransactions().sorted(by: { $0.date < $1.date }) {
            var array = history[Calendar.current.startOfDay(for: tx.date)]
            
            if array != nil {
                array!.append(tx)
            } else {
                array = [tx]
            }
            
            history[Calendar.current.startOfDay(for: tx.date)] = array
        }
        
        tableView.reloadData()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        
        history.removeAll(keepingCapacity: true)
    }
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return history.count
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return dateFormatter.string(from: history.keys.sorted()[section])
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return history[history.keys.sorted()[section]]!.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier, for: indexPath)
        let tx = history[history.keys.sorted()[indexPath.section]]![indexPath.row]
        
        let timeLabel = cell.contentView.viewWithTag(1) as? UILabel
        timeLabel?.text = timeFormatter.string(from: tx.date)
        
        let imageView = cell.contentView.viewWithTag(2) as? UIImageView
        imageView?.tintColor = tx.status == .declined ? .red : UIColor(named: "Tint")
        
        let textLabel = cell.contentView.viewWithTag(3) as? UILabel
        let value = NSDecimalNumber(string: tx.payment.amount, locale: numberFormatter.locale)
        textLabel?.text = "Para: \(tx.payment.receiverName)\n" +
                          "Valor: R$ \(numberFormatter.string(from: value)!)\n" +
                          "Cartão: Final \(tx.payment.cardNumber.suffix(4))"
        
        return cell
    }
}
