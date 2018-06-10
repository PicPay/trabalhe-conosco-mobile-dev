//
//  WebClient.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 09/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

enum WebError: Error {
    case serverUnreachable
    case unexpectedCondition(String)
}

extension WebError: LocalizedError {
    var errorDescription: String? {
        switch self {
        case .serverUnreachable:
            return "Não foi possível conectar."
        case .unexpectedCondition(let message):
            return message
        }
    }
}

final class WebClient {
    private var baseUrl: URL
    
    init(baseUrl: URL) {
        self.baseUrl = baseUrl
    }
    
    func request(path: String, body: Data?, completion: @escaping (Data?, WebError?) -> ()) -> URLSessionDataTask? {
        var urlRequest = URLRequest(url: URL(fileURLWithPath: path, relativeTo: baseUrl))
        urlRequest.httpMethod = body == nil ? "GET" : "POST"
        
        let dataTask = URLSession.shared.dataTask(with: urlRequest) { data, response, error in
            
            guard let _ = response else {
                completion(nil, .serverUnreachable)
                return
            }
            
            guard let httpResponse = response as? HTTPURLResponse, (200..<300) ~= httpResponse.statusCode else {
                completion(nil, .unexpectedCondition(error!.localizedDescription))
                return
            }
            
            completion(data, nil)
        }
        
        dataTask.resume()
        
        return dataTask
    }
}
