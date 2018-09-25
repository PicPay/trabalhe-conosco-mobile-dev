//
//  UserViewCollectionViewCell.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 17/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

class UserViewCollectionViewCell: UICollectionViewCell {
    
    public let imageView : UIImageView
    public let nameLabel : UILabel
    
    public override init(frame: CGRect) {
        var childFrame = CGRect(x: 0, y: 0, width: frame.width, height: frame.height * 1.6)
        imageView = UIImageView(frame: childFrame)
        imageView.contentMode = .scaleAspectFit
        imageView.layer.borderColor = UIColor.white.cgColor
        imageView.layer.borderWidth = 2
        imageView.layer.cornerRadius = imageView.frame.height / 2
        imageView.clipsToBounds = true
        
        
        childFrame.origin.y += childFrame.height + 16
        childFrame.size.height = 30
        nameLabel = UILabel(frame: childFrame)
        nameLabel.textColor = .white
        nameLabel.font = UIFont.boldSystemFont(ofSize: 12)
        nameLabel.textAlignment = .center
        
        super.init(frame: frame)
        
        backgroundColor = .clear
        addSubview(imageView)
        addSubview(nameLabel)
        
    }
    
    @available(*, unavailable)
    public required init?(coder: NSCoder) {
        fatalError("init?(coder:) is not supported")
    }
}
