//
//  TableFormVC.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

extension NewCredCardFormVC {
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: self.cellid, for: indexPath) as! BaseStaticCell
        self.form.cvvCredcard.delegate = self
        self.form.dateCredcard.delegate = self
        self.form.nameOwnerCredcard.delegate = self
        self.form.numberCredcard.delegate = self
        
        cell.addSubview(self.form)
        self.form.fillSuperview()
        return cell
    }
}
