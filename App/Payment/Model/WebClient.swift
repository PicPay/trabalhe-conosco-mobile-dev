//
//  WebClient.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 09/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

enum Response<T> {
    case success(T)
    case failure(Error)
}

final class WebClient {
    private var baseUrl: URL
    
    init(baseUrl: URL) {
        self.baseUrl = baseUrl
    }
    
    func request(path: String, body: Data?, completion: @escaping (Response<Data>) -> ()) -> URLSessionDataTask? {
        var urlRequest = URLRequest(url: URL(fileURLWithPath: path, relativeTo: baseUrl))
        urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
        urlRequest.httpMethod = body == nil ? "GET" : "POST"
        urlRequest.httpBody = body
        
        let dataTask = URLSession.shared.dataTask(with: urlRequest) { data, response, error in
            guard let _ = response else {
                completion(.failure(error!))
                return
            }
            
            guard let httpResponse = response as? HTTPURLResponse, (200..<600) ~= httpResponse.statusCode else {
                completion(.failure(error!))
                return
            }
            
            completion(.success(data!))
        }
        
        dataTask.resume()
        
        return dataTask
    }
}
