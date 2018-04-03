//
//  CircularImageView.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 28/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//
import Foundation
import UIKit

class UICircularImageView : UIImageView {
    override func layoutSubviews() {
        super.layoutSubviews()
        layer.cornerRadius = frame.height / 2.0
        layer.masksToBounds = true
        layer.borderColor = UIColor.white.cgColor
    }
}
