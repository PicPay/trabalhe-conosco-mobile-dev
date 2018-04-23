//
//  UIImageView.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

import UIKit

extension UIImageView {
    
    /// Carrega image de um url fazendo um cache da requisição para que assim não seja necessário
    /// carregar sempre a imagem.
    ///
    /// - Parameters:
    ///   - url: url de onde a imagem vai ser carregada
    ///   - placeholder: imagem que vai ficar de placeholder até que a imagem original seja carregada
    ///   - showActivityIndicator: mostra ou não o activity (loadding)
    ///   - cache: coloca ou não um cache especifico, caso não coloquei é pego o cache do shared
    func loadImage(from url: URL, placeholder: UIImage? = nil, showActivityIndicator: Bool = false, cache: URLCache? = nil) {
        
        let activity: UIActivityIndicatorView = UIActivityIndicatorView(activityIndicatorStyle: .gray)
        activity.center = CGPoint(x: self.frame.width / 2, y: self.frame.height / 2)
        activity.startAnimating()
        
        if showActivityIndicator {
            self.addSubview(activity)
        }
        self.setNeedsLayout()
        self.layoutIfNeeded()
        
        let cache = cache ?? URLCache.shared
        let request = URLRequest(url: url)
        if let data = cache.cachedResponse(for: request)?.data, let image = UIImage(data: data) {
            DispatchQueue.main.async {
                activity.removeFromSuperview()
                self.image = image
            }
        } else {
            self.image = placeholder
            URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) in
                if let data = data, let response = response, ((response as? HTTPURLResponse)?.statusCode ?? 500) < 300, let image = UIImage(data: data) {
                    let cachedData = CachedURLResponse(response: response, data: data)
                    cache.storeCachedResponse(cachedData, for: request)
                    DispatchQueue.main.async {
                        activity.removeFromSuperview()
                        self.image = image
                    }
                } else {
                    DispatchQueue.main.async {
                        activity.removeFromSuperview()
                    }
                }
            }).resume()
        }
    }
    
    /// Imagem em formato de circulo
    /// não é feito o recorte da imagem, apenas se coloca uma mascara da view para que
    /// fiquei com aspecto arredondado
    func roundedImage() {
        self.clipsToBounds = true
        self.layer.cornerRadius = self.bounds.width / 2;
    }
    
}
