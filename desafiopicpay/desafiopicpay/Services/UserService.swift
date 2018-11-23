//
//  UserService.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class UserService {
    
    func list(completionHandler:@escaping( (Data?, URLResponse?, Error?) -> Void )) {
        let uri = URL(string: "http://careers.picpay.com/tests/mobdev/users")
        
        let headers: [String: String] = ["Content-Type": "application/json"]
        
        var request = URLRequest(url: uri!)
        request.allHTTPHeaderFields = headers
        request.httpMethod = HTTPMethods.get
        
        let task = URLSession.shared.dataTask(with: request, completionHandler: completionHandler)
        task.resume()
    }
    
}
