//
//  CardTableViewCell.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/18/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import UIKit

class CardTableViewCell: UITableViewCell {
    @IBOutlet weak var selectionView: UIView! {
        didSet {
            self.selectionView.backgroundColor = UIColor.white
            self.selectionView.layer.cornerRadius = self.selectionView.frame.width/2
            self.selectionView.clipsToBounds = true
        }
    }
    
    @IBOutlet weak var cardNumberLabel: UILabel!
    @IBOutlet weak var expiryDateLabel: UILabel!
    @IBOutlet weak var cvvLabel: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    func setUpCell(card: CardCoreData, isSelected: Bool) {
        self.expiryDateLabel.text = card.expiryDate
        self.cardNumberLabel.text = StringUtils.formatCreditCardNumberWithEncryption(cardNumber: card.cardNumber)
        self.selectionView.backgroundColor = isSelected ? .black : .white
    }
    
}
