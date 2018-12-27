//
//  NavBarViewModel.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 08/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit
import SDWebImage

struct NavBarViewModel {
    let username: String
    let img: String
    
    let imageView = UIImageView()
    
    init(contato: Contato) {
        self.username = contato.username
        self.img = contato.img
        
        self.imageView.sd_setImage(with: URL(string: self.img), placeholderImage: UIImage(named: "noprofile.png"))
    }
}
