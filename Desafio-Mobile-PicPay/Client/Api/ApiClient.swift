
//
//  ApiClient.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import Foundation
import UIKit

class ApiClient{
    
    lazy var appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    func taskForGETMethod(stringURL: String, completion: @escaping (_ data: Data?, _ errorMessage: String? )->Void){
        
        guard let url = URL(string: stringURL) else {return}
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            
            if let error = error {
                print("Error to Api Get Mothod: \(error.localizedDescription)")
                completion(nil, error.localizedDescription)
                return
            }
            guard let response = response as? HTTPURLResponse, let jsonData = data else {completion(nil, "Error"); return}
            response.statusCode == 200 ? completion(jsonData, nil): completion(nil, "Error")
            
        }.resume()
    }
    
    func taskForPOSTMethod(stringURL: String, jsonBody: String ,completion: @escaping (_ data: Data?, _ errorMessage: String? )->Void){
        
        guard let url = URL(string: stringURL) else {return}
        let request = NSMutableURLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonBody.data(using: String.Encoding.utf8)
        let header = [ClientStrings.ContentType: ClientStrings.JsonFormat]
        request.allHTTPHeaderFields = header
                
        URLSession.shared.dataTask(with: request as URLRequest) { (data, response, error) in
            
            if let error = error {
                print("Error to Api Get Mothod: \(error.localizedDescription)")
                completion(nil, error.localizedDescription)
                return
            }
            guard let response = response as? HTTPURLResponse, let jsonData = data else {completion(nil, "Error"); return}
            response.statusCode == 200 ? completion(jsonData, nil): completion(nil, "Error")
        }.resume()
    }
}
