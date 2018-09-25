//
//  CardTableViewCell.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 20/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

class CardTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var finalCardLabel: UILabel!
    @IBOutlet weak var mainCardLabel: UILabel!
    
    
//    public override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
//        super.init(style: style, reuseIdentifier: reuseIdentifier)
//    }
    
//    required init?(coder aDecoder: NSCoder) {
//        super.init(coder: aDecoder)
//        //fatalError("init(coder:) has not been implemented")
//    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
