//
//  CredcardListUIViewCell.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class CredcardListUIViewCell: UITableViewCell {
    // MARK: - Properties
    static let storyboardId = "CredcardListUIViewCell"
    
    // Instantiate the Nib file
    class func instanceFromNib() -> UINib {
        return UINib(nibName: storyboardId, bundle: nil)
    }
    
    // MARK: - Outlets
    @IBOutlet weak var lblCardNumber: UILabel!
    @IBOutlet weak var lblIsMainCard: UILabel!
    @IBOutlet weak var lblCardValidate: UILabel!
    @IBOutlet weak var viewContainer: UIView!
    
    public override func awakeFromNib() {
        viewContainer.layer.cornerRadius = 5
        lblIsMainCard.text = ""
    }
    
    public func update(with card: Card) {
        lblCardNumber.text = "\(card.number)"
        lblCardValidate.text = card.validate
        if card.isMainCard {
            lblIsMainCard.text = "Principal"
        }
    }
}
