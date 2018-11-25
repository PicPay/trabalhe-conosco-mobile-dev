//
//  UIButtonNavbar.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

class UIButtonNavbar: UIButton {
    
    required init() {
        super.init(frame: .zero)
    }
    
    convenience init(title: String) {
        self.init()
        
        setTitle("Cancelar", for: .normal)
        titleLabel?.adjustsFontSizeToFitWidth = true
        titleLabel?.font = UIFont.systemFont(ofSize: 17.0)
        setTitleColor(UIColor(hex: Env.Palette.green), for: .normal)
        layer.masksToBounds = false
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
