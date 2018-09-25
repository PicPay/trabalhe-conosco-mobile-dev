//
//  MainCardView.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 22/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

class MainCardView: UIView {
    
    public let cardImageView: UIImageView
    public let nameLabel: UILabel
    public let detailLabel : UILabel
    public let principalLabel: UILabel
    
    public override init(frame: CGRect) {
        let childFrame = CGRect(x: 10, y: 0, width: frame.height, height: frame.height)
        cardImageView = UIImageView(frame: childFrame)
        cardImageView.contentMode = .scaleAspectFit
        cardImageView.image = UIImage(named: "picpay_card")
        
        let nameLabelFrame = CGRect(x: 15 + frame.height, y: 8, width: frame.width / 2, height: frame.height / 3)
        nameLabel = UILabel(frame: nameLabelFrame)
        nameLabel.font = .systemFont(ofSize: 14)
        nameLabel.textColor = .darkGray
        
        let detailLabelFrame = CGRect(x: 15 + frame.height, y: 8 + frame.height / 3, width: frame.width / 2, height: frame.height / 3)
        detailLabel = UILabel(frame: detailLabelFrame)
        detailLabel.font = .systemFont(ofSize : 11)
        detailLabel.textColor = .lightGray
        
        
        let principalLabelFrame = CGRect(x: frame.width * 0.8, y: 8 + (frame.height / 2) - (frame.height / 3) , width: frame.width / 4, height: frame.height / 3)
        principalLabel = UILabel(frame: principalLabelFrame)
        principalLabel.font = .systemFont(ofSize: 11)
        principalLabel.textColor = .lightGray
        principalLabel.text = "Principal"
        
        super.init(frame: frame)
        //backgroundColor = .black
        addSubview(cardImageView)
        addSubview(nameLabel)
        addSubview(detailLabel)
        addSubview(principalLabel)
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

}
