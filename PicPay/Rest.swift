//
//  Rest.swift
//  PicPay
//
//  Created by Vinicius Alves on 09/03/2018.
//  Copyright © 2018 TIPiniquim. All rights reserved.
//

import Foundation
import UIKit

enum ClienteError {
    case url
    case taskError(Error)
    case noResponse
    case noData
    case responseStatusCode(code: Int)
    case invalidJson
}

enum CartaoError {
    case url
    case taskError(Error)
    case noResponse
    case noData
    case responseStatusCode(code: Int)
    case invalidJson
}

class REST {
    
    private static let getURL: String = "http://careers.picpay.com/tests/mobdev/users"
    private static let postURL: String = "http://careers.picpay.com/tests/mobdev/transaction"
    
    private static let configuration: URLSessionConfiguration = {
        let config = URLSessionConfiguration.default
        config.allowsCellularAccess = false
        config.httpAdditionalHeaders = ["Content-Type": "application/json"]
        config.timeoutIntervalForRequest = 30.0
        config.httpMaximumConnectionsPerHost = 5
        
        return config
    }()
    
    private static let session: URLSession = URLSession(configuration: configuration) // URLSession.shared
    
    class func loadClientes(onComplete: @escaping ([Cliente]) -> Void, onError: @escaping (ClienteError) -> Void) {
        guard let url = URL(string: getURL) else {
            onError(.url)
            return
        }
        
        let dataTask = session.dataTask(with: url) { (dada: Data?, response: URLResponse?, error: Error?) in
            
            if error == nil {
                
                guard let response = response as? HTTPURLResponse else {
                    onError(.noResponse)
                    return
                }
                if response.statusCode == 200 {
                    
                    guard let data = dada else {return}
                    do {
                        
                        let clientes = try JSONDecoder().decode([Cliente].self, from: data)
                        onComplete(clientes)
                        
                    } catch {
                        onError(.invalidJson)
                        print(error.localizedDescription)
                    }
                    
                } else {
                    print("Status inválido pelo servidor.")
                    onError(.responseStatusCode(code: response.statusCode))
                }
                
            } else {
                onError(.taskError(error!))
                print(error!)
            }
        }
        dataTask.resume()
    }
    
    /** ---------------------------------- */
    
    class func concluirTransacao(cartao: Cartao, onComplete: @escaping (Bool) -> Void) {
        
        guard let url = URL(string: postURL) else {
            print("Erro na URL")
            onComplete(false)
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        guard let json = try? JSONEncoder().encode(cartao) else {
            print("Erro no Json")
            onComplete(false)
            return
        }
        
        request.httpBody = json
        
        let dataTask = session.dataTask(with: request) { (data, response, error) in
            if error == nil {
                guard let response = response as? HTTPURLResponse, response.statusCode == 200, let _ = data else {
                    print("Erro no response")
                    onComplete(false)
                    return
                }
                
                onComplete(true)
            } else {
                print("Erro na task")
                onComplete(false)
            }
        }
        
        dataTask.resume()
    }
}




























