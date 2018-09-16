//
//  UsersListTableViewCell.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 12/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import UIKit
import Kingfisher

class UsersListTableViewCell: UITableViewCell {

    @IBOutlet weak var userImgView: UIImageView!
    @IBOutlet weak var userAccountLbl: UILabel!
    @IBOutlet weak var userNameLbl: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setupCell(user: User) {
        self.userNameLbl.text = user.name
        self.userAccountLbl.text = user.username
        self.userImgView.kf.setImage(with: URL(string: user.img))
        self.userImgView.kf.indicatorType = .activity
        self.userImgView.layer.cornerRadius = self.userImgView.frame.height/2
        self.userImgView.clipsToBounds = true

    }
}
