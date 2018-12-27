//
//  PrimingView.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class PrimingView: UIView {
    
    let imgcredcard: UIImageView = {
        let img = UIImageView(image: #imageLiteral(resourceName: "cred-card"))
        img.contentMode = .scaleAspectFit
        return img
    }()
    let titlelabel: UILabel = {
        let label = UILabel()
        label.textColor = .white
        label.numberOfLines = 3
        label.textAlignment = .center
        label.font = UIFont.boldSystemFont(ofSize: 29)
        label.text = "Cadastre um cartão de Crédito"
        return label
    }()
    let descTitlelabel: UILabel = {
        let label = UILabel()
        label.textColor = .white
        label.textAlignment = .center
        label.numberOfLines = 5
        label.text = "Para fazer pagamentos para outras pessoas, você precisa cadastrar um cartão de crédito pessoal."
        return label
    }()
    
    let assignButton: DefaultButton = {
        let button = DefaultButton()
        button.setTitle("Cadastrar cartão", for: .normal)
        return button
    }()

    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = .clear
        configure()
    }
    fileprivate func configure() {
        self.addSubview(self.imgcredcard)
        self.addSubview(self.titlelabel)
        self.addSubview(self.descTitlelabel)
        self.addSubview(self.assignButton)
        
        self.titlelabel.anchorXY(centerX: self.centerXAnchor, centerY: nil, top: nil, leading: nil, bottom: self.centerYAnchor, trailing: nil, padding: .init(top: 0, left: 0, bottom: 20, right: 0), size: .init(width: 300, height: 100))
//
        self.imgcredcard.anchorXY(centerX: self.centerXAnchor, centerY: nil, top: nil, leading: nil, bottom: self.titlelabel.topAnchor, trailing: nil, padding: .init(top: 0, left: 0, bottom: 5, right: 0), size: .init(width: 150, height: 100))
//
        self.descTitlelabel.anchorXY(centerX: self.centerXAnchor, centerY: nil, top: self.titlelabel.bottomAnchor, leading: nil, bottom: nil, trailing: nil, padding: .init(top: 5, left: 0, bottom: 0, right: 0), size: .init(width: 320, height: 100))
//
        self.assignButton.anchor(top: nil, leading: self.leadingAnchor, bottom: self.bottomAnchor, trailing: self.trailingAnchor, padding: .init(top: 0, left: 0, bottom: 15, right: 0), size: .init(width: 0, height: 60))
    }
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
