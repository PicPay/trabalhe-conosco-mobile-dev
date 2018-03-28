//
//  RestApi.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 28/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation
import Alamofire

enum RestApi : URLRequestConvertible {
    static let baseURLString = "http://careers.picpay.com/tests/mobdev/"
    
    case getUsers()
    case sendTransaction([String:Any])
    
    func asURLRequest() throws -> URLRequest {
        var method: HTTPMethod {
            switch self {
            case .getUsers:
                return .get
            case .sendTransaction:
                return .post
            }
        }
        
        let params: ([String:Any]?) = {
            switch self {
            case .getUsers:
                return nil
            case .sendTransaction(let data):
                return data
            }
        }()
        
        let url: URL = {
            //build up and return the URL for each endpoint
            let relativePath: String?
            
            switch self {
            case .getUsers:
                relativePath = Const.getUsersPath
            case .sendTransaction:
                relativePath = Const.sendTransactionPath
            }
            
            var url = URL(string: RestApi.baseURLString)!
            if let relativePath = relativePath {
                url.appendPathComponent(relativePath)
            }
            return url
        }()
        
        var urlRequest = URLRequest(url: url)
        urlRequest.httpMethod = method.rawValue
        
        let encoding: ParameterEncoding = {
            switch method {
            case .get:
                return URLEncoding.default
            default:
                return JSONEncoding.default
            }
        }()
        return try encoding.encode(urlRequest, with: params)
    }
}

