//
//  ImageUtils.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 28/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import UIKit

class ImageUtils {
    static func downloadImage(_ urlString: String, completion: @escaping (_ img: UIImage) -> Void) {
        let pictureURL = URL(string: urlString)!
        let session = URLSession(configuration: .default)
        
        let downloadPicTask = session.dataTask(with: pictureURL) { (data, response, error) in
            if let e = error {
                print("Error downloading user picture: \(e)")
            } else {
                if (response as? HTTPURLResponse) != nil {
                    var image: UIImage?
                    if let imageData = data {
                        image = UIImage(data: imageData)
                    } else {
                        let userGenre = urlString.contains("woman") ? "woman" : "man"
                        image = UIImage(named: "user-\(userGenre)")
                    }
                    completion(image!)
                } else {
                    print("Couldn't get response code for some reason")
                }
            }
        }
        
        downloadPicTask.resume()
    }
}
