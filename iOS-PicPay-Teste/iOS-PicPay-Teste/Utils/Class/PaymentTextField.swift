//
//  PaymentTextField.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 04/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class PaymentTextField: UITextField, UITextFieldDelegate {
    let borderView: UILabel = {
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 50, height: 50))
        label.textColor = .darkGray
        label.text = "R$"
        label.textAlignment = .right
        label.font = UIFont(name: "HelveticaNeue-Thin", size: 29)
        return label
    }()
    
    var placeholderCustom: String? {
        didSet {
            super.attributedPlaceholder = NSAttributedString(string: placeholderCustom ?? "", attributes: [NSAttributedString.Key.foregroundColor:UIColor.darkGray])
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        textColor = .white
        layer.backgroundColor = UIColor.clear.cgColor
        layer.masksToBounds = false
        textColor = .lightGreen
        textAlignment = .center
        font = UIFont(name: "AppleSDGothicNeo-Light", size: 80)
        keyboardType = .numberPad
        
        leftViewMode = .always
        leftView = borderView
    }
    
    //MARK:- Animations
    func changeColor() {
            self.borderView.textColor = .lightGreen
            super.attributedPlaceholder = NSAttributedString(string: placeholderCustom ?? "", attributes: [NSAttributedString.Key.foregroundColor:UIColor(red: 17/255, green: 199/255, blue: 111/255, alpha: 1.0)])
    }
    func returnDefaultColor() {
        if self.text == "" {
                self.borderView.textColor = .darkGray
                super.attributedPlaceholder = NSAttributedString(string: placeholderCustom ?? "", attributes: [NSAttributedString.Key.foregroundColor:UIColor.darkGray])
        }
    }
    //MARK:- Configure Custom Class
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

