//
//  User.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/17/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import UIKit

class User {
    let name: String
    let id: Int
    let imgUrl: String
    let username: String
    var image: UIImage?
    var isLoadingImage: Bool = false
    
    init?(dict: [String: Any]) {
        guard let name = dict[UserKeys.name] as? String, let id = dict[UserKeys.id] as? Int, let imgUrl = dict[UserKeys.imageUrl] as? String, let username = dict[UserKeys.username] as? String else {
            return nil
        }
        
        self.id = id
        self.name = name
        self.imgUrl = imgUrl
        self.username = username
    }
    
    func downloadImage() {
        self.isLoadingImage = true
        AlamofireService.downloadImage(imageUrl: self.imgUrl) { (success, image) in
            DispatchQueue.main.async {
                if success, let image = image {
                    self.image = image
                    NotificationCenter.default.post(name: .userImageDownloadCompleted, object: nil, userInfo: [NotificationKeys.userId: self.id])
                }
                self.isLoadingImage = false 
            }
        }
    }
    
    func needsToDownloadImage() -> Bool {
        return !self.isLoadingImage && !self.imgUrl.isEmpty
    }
}
