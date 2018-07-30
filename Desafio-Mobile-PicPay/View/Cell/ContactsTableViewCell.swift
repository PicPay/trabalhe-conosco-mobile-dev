//
//  ContactsTableViewCell.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class ContactsTableViewCell: UITableViewCell {

    @IBOutlet weak var img: CustomImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    
    var contact: Contact?{
        didSet{
            guard let contact = contact else {return}
            userNameLabel.text = contact.username
            nameLabel.text = contact.name
            
            if (CustomImageView.imagesCache[contact.img] != nil){
                img.image = CustomImageView.imagesCache[contact.img]
                return
            }
            img.downloadImageView(contact.img)
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
}
