//
//  CustomImageView.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import UIKit

class CustomImageView: UIImageView {
    
    static var imagesCache = [String: UIImage]()
    
    func downloadImageView(_ stringURL: String){
        DispatchQueue.global().async {
            guard let url = URL(string: stringURL) else {return}
            guard let imageData = try? Data(contentsOf: url) else {return}
            DispatchQueue.main.async {
                self.image = UIImage(data: imageData)
                CustomImageView.imagesCache[stringURL] = self.image
            }            
        }
    }
}
