//
//  CustomNavView.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 26/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class CustomNavView: UIView {
    
    let title: UILabel = {
        let label = UILabel(frame: CGRect(x: 20, y: 74, width: 150, height: 30))
        label.font = UIFont.boldSystemFont(ofSize: 36)
        label.text = "Contatos"
        label.textColor = .white
        return label
    }()
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }

    fileprivate func configure() {
        self.addSubview(self.title)
        title.anchor(top: self.centerYAnchor, leading: self.leadingAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(top: -15, left: 0, bottom: 0, right: 0))
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
