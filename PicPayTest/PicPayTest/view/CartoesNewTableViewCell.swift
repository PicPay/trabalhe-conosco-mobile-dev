//
//  CartoesNewTableViewCell.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 16/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import UIKit

class CartoesNewTableViewCell: UITableViewCell {

    @IBOutlet weak var newCartImgView: UIImageView!
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var subtitleLbl: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
