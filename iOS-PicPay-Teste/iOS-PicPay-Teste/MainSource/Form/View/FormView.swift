//
//  FormViewCell.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class FormView: UIView {
    
    let numberCredcard: DefaultTextField = {
        let field = DefaultTextField()
        field.placeholderCustom = "Número do cartão"
        return field
    }()
    let nameOwnerCredcard: DefaultTextField = {
        let field = DefaultTextField()
        field.keyboardType = .default
        field.autocapitalizationType = .words
        field.placeholderCustom = "Nome do titular"
        return field
    }()
    let dateCredcard: DefaultTextField = {
        let field = DefaultTextField()
        field.placeholderCustom = "Vencimento"
        return field
    }()
    let cvvCredcard: DefaultTextField = {
        let field = DefaultTextField()
        field.placeholderCustom = "CVV"
        return field
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    
    fileprivate func configure() {
        self.backgroundColor = .clear
        
        self.addSubview(self.numberCredcard)
        self.addSubview(self.nameOwnerCredcard)
        self.addSubview(self.dateCredcard)
        self.addSubview(self.cvvCredcard)
        
        self.numberCredcard.anchor(top: self.topAnchor, leading: self.leadingAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(top: 60, left: 10, bottom: 0, right: 10), size: .init(width: 0, height: 30))
        
        self.nameOwnerCredcard.anchor(top: self.numberCredcard.bottomAnchor, leading: self.leadingAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(top: 60, left: 10, bottom: 0, right: 10), size: .init(width: 0, height: 30))
        
        self.dateCredcard.anchor(top: self.nameOwnerCredcard.bottomAnchor, leading: self.leadingAnchor, bottom: nil, trailing: self.centerXAnchor, padding: .init(top: 60, left: 10, bottom: 0, right: 10), size: .init(width: 0, height: 30))
        
        self.cvvCredcard.anchor(top: self.nameOwnerCredcard.bottomAnchor, leading: self.centerXAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(top: 60, left: 10, bottom: 0, right: 10), size: .init(width: 0, height: 30))
        
    }
    
    
    func configureNotEmpty() {
        self.nameOwnerCredcard.animationToTop()
        self.nameOwnerCredcard.animationToDown()
        
        self.numberCredcard.animationToTop()
        self.numberCredcard.animationToDown()
        
        self.cvvCredcard.animationToTop()
        self.cvvCredcard.animationToDown()
        
        self.dateCredcard.animationToTop()
        self.dateCredcard.animationToDown()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
