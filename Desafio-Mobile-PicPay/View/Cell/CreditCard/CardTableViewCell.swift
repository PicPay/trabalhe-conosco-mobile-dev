//
//  CardTableViewCell.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class CardTableViewCell: UITableViewCell {

    @IBOutlet weak var numberLabel: UILabel!
    
    var card: Card?{
        didSet{
            guard let cardNumber = card?.card_number else {return}            
            numberLabel.text = "Terminado em \(cardNumber.suffix(4))"
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
}
