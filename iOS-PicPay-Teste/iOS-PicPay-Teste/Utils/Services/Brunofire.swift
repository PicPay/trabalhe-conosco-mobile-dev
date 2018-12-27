//
//  Brunofire.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 19/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import Foundation

struct BrunoFire {
    
    enum HTTPmethod: String {
        case post
        case get
    }
    
    func request(_ urlstring: String, method: HTTPmethod = .get, parameters: [String:Any] = [:], headers: [String:String] = [:], completed: @escaping (_ success:Data) ->()) {
        
        let url = URL(string: urlstring.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!)
        
        var request = URLRequest(url: url!)
        request.httpMethod = method.rawValue
        
        if headers.count > 0 {
            request.allHTTPHeaderFields = headers
        }
        
        if parameters.count > 0 {
            do {
                request.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: .prettyPrinted)
            } catch let error {
                print(error.localizedDescription)
            }
        }
        
        let task = URLSession.shared.dataTask(with: request) { data, _, error in
            guard error == nil else {
                print(error!)
                return
            }
            guard let data = data else {
                return
            }
            completed(data)
        }
        task.resume()
    }
}
