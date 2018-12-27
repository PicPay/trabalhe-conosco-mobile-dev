//
//  CheckingVC.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

extension CheckingViewController {
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.row {
        case 0:
            return 260
        case 1:
            return 55
        default:
            return 65
        }
    }
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch indexPath.row {
        case 0:
            let cell = tableView.dequeueReusableCell(withIdentifier: self.cell, for: indexPath) as! CheckingCell
            let ticketusermodel = TicketViewModelUser(contato: self.contato, pay_id: self.payment)
            cell.user = ticketusermodel
            return cell
        case 1:
            let cell = tableView.dequeueReusableCell(withIdentifier: self.cellcred, for: indexPath) as! CellCredCard
            let credcard = self.payment.map( {return TicketViewModelCredCard(pay: $0 )} )
            cell.cartao = credcard
            return cell
        default:
            let cell = tableView.dequeueReusableCell(withIdentifier: self.celltotal, for: indexPath) as! CellTotalPayment
            let value = self.payment.map( {return TicketViewModelTotal(pay: $0)} )
            cell.total = value
            self.tableView.separatorStyle = .none
            return cell
        }
    }
}
