//
//  CardInfoCell.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 28/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import UIKit

class CardInfoCell: UITableViewCell {

    @IBOutlet weak var txtCardInfo: UITextField!
    @IBOutlet weak var lblCardInfo: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func fillCell(card: CardInfo, index: Int) {
        lblCardInfo.text = card.labelCardInfo
        txtCardInfo.keyboardType = card.keyboardType
        txtCardInfo.placeholder = card.placeholder
        txtCardInfo.tag = index
    }
}
