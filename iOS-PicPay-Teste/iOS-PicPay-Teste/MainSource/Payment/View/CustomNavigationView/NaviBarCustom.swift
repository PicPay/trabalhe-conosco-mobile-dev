//
//  NaviBarCustom.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 05/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class NaviBarCustom: UIView {
    
    let imgavatar: UIImageView = {
        let image = UIImageView(image: #imageLiteral(resourceName: "noprofile"))
        image.layer.cornerRadius = 22.5
        image.layer.masksToBounds = true
        image.contentMode = .scaleAspectFill
        return image
    }()
    
    let labelnickname: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "HelveticaNeue-Regular", size: 19)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        label.textAlignment = .center
        return label
    }()
    
    var contatoViewModel: NavBarViewModel! {
        didSet {
            self.labelnickname.text = contatoViewModel.username
            self.imgavatar.image = contatoViewModel.imageView.image
        }
    }
    
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    fileprivate func configure() {
        self.addSubview(self.labelnickname)
        self.addSubview(self.imgavatar)
        
        self.labelnickname.anchor(top: nil, leading: self.leadingAnchor, bottom: self.bottomAnchor, trailing: self.trailingAnchor, padding: .init(top: 0, left: 10, bottom: 0, right: 10), size: .init(width: 0, height: 20))
        self.imgavatar.anchorXY(centerX: self.labelnickname.centerXAnchor, centerY: nil, top: nil, leading: nil, bottom: self.labelnickname.topAnchor, trailing: nil, padding: .init(), size: .init(width: 45, height: 45))
    }
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
