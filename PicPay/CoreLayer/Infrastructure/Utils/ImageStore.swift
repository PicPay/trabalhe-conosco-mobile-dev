//
//  ImageStore.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

enum ImageStoreResult {
    case success(UIImage)
    case failure(Error)
}

enum ImageStoreError: Error {
    case imageCreatingError
}

class ImageStore {
    let cache = NSCache<NSString, UIImage>()
    
    private let session: URLSession = {
        let config = URLSessionConfiguration.default
        return URLSession(configuration: config)
    }()
    
    // Singleton
    static let shared: ImageStore = ImageStore()
    private init() {}
    
    func setImage(_ image: UIImage, forKey key: String) {
        self.cache.setObject(image, forKey: key as NSString)
        
        //Create full URL for image
        let url = self.imageURL(forKey: key)
        
        //Turn image into JPEG data
        if let data = image.jpegData(compressionQuality: 0.5) {
            //Write it to full URL
            _ = try? data.write(to: url, options: [.atomic])
        }
    }
    
    func image(forKey key: String) -> UIImage? {
        if let existingImage = self.cache.object(forKey: key as NSString) {
            return existingImage
        }
        
        let url = self.imageURL(forKey: key)
        guard let imageFromDisk = UIImage(contentsOfFile: url.path) else { return nil }
        
        self.cache.setObject(imageFromDisk, forKey: key as NSString)
        
        return imageFromDisk
    }
    
    func deleteImage(forKey key: String) {
        self.cache.removeObject(forKey: key as NSString)
        
        let url = self.imageURL(forKey: key)
        do {
            try FileManager.default.removeItem(at: url)
        } catch let deleteError {
            print("Error removing the image from disk: \(deleteError)")
        }
    }
    
    func imageURL(forKey key: String) -> URL {
        let documentsDirectories = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let documentDirectory = documentsDirectories.first!
        
        return documentDirectory.appendingPathComponent(key)
    }
    
    func fetchImage(for key: String, from url: URL, completion: @escaping (ImageStoreResult) -> Void) {
        if let img = self.image(forKey: key) {
            OperationQueue.main.addOperation {
                completion(.success(img))
            }
            
            return
        }
        
        let request = URLRequest(url: url)
        let task = self.session.dataTask(with: request) { (data, _, error) -> Void in
            
            let result = self.proccessImageRequest(data: data, error: error)
            
            if case let .success(image) = result {
                self.setImage(image, forKey: key)
            }
            
            OperationQueue.main.addOperation {
                completion(result)
            }
        }
        task.resume()
    }
    
    private func proccessImageRequest(data: Data?, error: Error?) -> ImageStoreResult {
        guard let imageData = data, let image = UIImage(data: imageData) else {
            if data == nil {
                return .failure(error!)
            } else {
                return .failure(ImageStoreError.imageCreatingError)
            }
        }
        
        return .success(image)
    }
}
