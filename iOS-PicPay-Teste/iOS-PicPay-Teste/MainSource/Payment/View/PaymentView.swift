//
//  PaymentView.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class PaymentView: UIView {
    
    let valuePayment: PaymentTextField = {
        let textf = PaymentTextField()
        textf.adjustsFontSizeToFitWidth = true
        textf.contentScaleFactor = 0.3
        textf.placeholderCustom = "0,00"
        return textf
    }()
    let credCard: UILabel = {
        let label = UILabel()
        label.text = "MasterCard 1234"
        label.textColor = .white
        label.font = UIFont.boldSystemFont(ofSize: 15)
        label.textAlignment = .right
        return label
    }()
    
    let editCredcard: UIButton = {
        let button = UIButton()
        button.backgroundColor = .clear
        button.setTitle("EDITAR", for: .normal)
        button.setTitleColor(.lightGreen, for: .normal)
        return button
    }()

    let separatorView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 2.5
        view.layer.masksToBounds = true
        view.backgroundColor = .white
        return view
    }()
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    
    fileprivate func configure() {
        self.backgroundColor = .clear
        self.addSubview(self.valuePayment)
        self.addSubview(self.credCard)
        self.addSubview(self.editCredcard)
        self.addSubview(self.separatorView)
        
        UITextField.appearance().tintColor = .lightGreen
        
        self.valuePayment.anchorXY(centerX: self.centerXAnchor, centerY: nil, top:nil, leading: nil, bottom: self.credCard.topAnchor, trailing: nil, padding: .init(top: 0, left: 0, bottom: 30, right: 0), size: .init(width: 275, height: 100))
        
        self.credCard.anchor(top: nil, leading: nil, bottom: self.centerYAnchor, trailing: self.valuePayment.centerXAnchor, padding: .init(top: 0, left: 0, bottom: 10, right: -15), size: .init(width: 0, height: 30))
        
        self.credCard.widthAnchor.constraint(lessThanOrEqualToConstant: 125).isActive = true
        
        self.editCredcard.anchor(top: nil, leading: self.separatorView.trailingAnchor, bottom: self.centerYAnchor, trailing: nil, padding: .init(top: 0, left: 5, bottom: 10, right:    0), size: .init(width: 0, height: 30))
        
        self.separatorView.anchorXY(centerX: nil, centerY: self.editCredcard.centerYAnchor, top: nil, leading: self.credCard.trailingAnchor, bottom: nil, trailing: nil, padding: .init(top: 0, left: 5, bottom: 0, right: 0), size: .init(width: 5, height: 5))
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
