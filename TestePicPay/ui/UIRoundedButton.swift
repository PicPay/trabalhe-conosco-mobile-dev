//
//  UIRoundedButton.swift
//  IPS
//
//  Created by Rodolfo Gusson on 04/12/17.
//  Copyright © 2017 GSVix. All rights reserved.
//

import Foundation
import UIKit

class UIRoundedButton: UIButton{
    
    override func layoutSubviews() {
        super.layoutSubviews()
        updateCornerRadius()
        titleLabel?.font = UIFont.systemFont(ofSize: 14, weight: UIFont.Weight.bold)
        setGradientBackground(colorOne: Colors.lightGreen, colorTwo: Colors.mediumGreen)
    }
    
    func updateCornerRadius(){
        layer.cornerRadius = 24
        clipsToBounds = true
    }
}
