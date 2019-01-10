//
//  MainListContactsCellTableViewCell.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 03/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class MainListContactsCell: BaseCell<ContatosViewModel> {

    let imgavatar: UIImageView = {
        let image = UIImageView()
        image.layer.cornerRadius = 25
        image.layer.masksToBounds = true
        image.contentMode = .scaleAspectFill
        return image
    }()
    let labelnickname: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "HelveticaNeue-Regular", size: 19)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        return label
    }()
    let labelname: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "HelveticaNeue-Regular", size: 14)
        label.textColor = .lightGray
        return label
    }()
    
    //Configure
    
    override var item: ContatosViewModel! {
        didSet {
            self.labelname.text = item.name
            self.labelnickname.text = item.username
            self.imgavatar.sd_setImage(with: URL(string: item.img), placeholderImage: UIImage(named: "noprofile.png"))
        }
    }
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.backgroundColor = .strongBlack
        configure()
    }
    
    fileprivate func configure() {
        self.selectionStyle = .none
        self.addSubview(self.imgavatar)
        self.addSubview(self.labelnickname)
        self.addSubview(self.labelname)
        
        self.imgavatar.anchorXY(centerX: nil, centerY: self.centerYAnchor, top: nil, leading: self.leadingAnchor, bottom: nil, trailing: nil, padding: .init(top: 0, left: 15, bottom: 0, right: 0), size: .init(width: 50, height: 50))
        
        self.labelnickname.anchor(top: nil, leading: self.imgavatar.trailingAnchor, bottom: self.centerYAnchor, trailing: self.trailingAnchor, padding: .init(top: 0, left: 15, bottom: 0, right: 10))
        self.labelname.anchor(top: self.centerYAnchor, leading: self.imgavatar.trailingAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(top: 0, left: 15, bottom: 0, right: 10))
    }
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    

}
