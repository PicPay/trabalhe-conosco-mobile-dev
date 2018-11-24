//
//  UIImageExtension.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

extension UIImage {
    var uncompressedPNGData: Data {
        return self.pngData()!
    }
    
    var highestQualityJPEGNSData: Data {
        return self.jpegData(compressionQuality: 1.0)!
    }
    
    var highQualityJPEGNSData: Data {
        return self.jpegData(compressionQuality: 0.75)!
    }
    
    var mediumQualityJPEGNSData: Data {
        return self.jpegData(compressionQuality: 0.5)!
    }
    
    var lowQualityJPEGNSData: Data {
        return self.jpegData(compressionQuality: 0.25)!
    }
    
    var lowestQualityJPEGNSData: Data {
        return self.jpegData(compressionQuality: 0)!
    }
    
    class func imageWithColor(color: UIColor) -> UIImage {
        let rect = CGRect(x: CGFloat(0.0), y: CGFloat(0.0), width: CGFloat(1.0), height: CGFloat(1.0)) //CGRectMake(0.0, 0.0, 1.0, 1.0)
        UIGraphicsBeginImageContext(rect.size)
        
        if let context = UIGraphicsGetCurrentContext() {
            context.setFillColor(color.cgColor)
            context.fill(rect)
        }
        
        if let image = UIGraphicsGetImageFromCurrentImageContext() {
            UIGraphicsEndImageContext()
            return image
        }
        
        return UIImage()
    }
}
