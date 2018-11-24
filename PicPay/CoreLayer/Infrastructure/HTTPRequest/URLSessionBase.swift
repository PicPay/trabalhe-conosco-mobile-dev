//
//  URLSessionBase.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

class URLSessionBase {
    enum HTTPMethod: String {
        case get = "GET"
        case post = "POST"
        case head = "HEAD"
        case put = "PUT"
        case delete = "DELETE"
        case patch = "PATCH"
    }
    
    enum URLSessionError: Error {
        case httpError(Int)
        case requestError
    }
    
    let session: URLSession
    let baseURL: URL
    
    init(session: URLSession, baseURL: URL) {
        self.session = session
        self.baseURL = baseURL
    }
    
    internal func request(urlPath: String, parameters: URLSessionParameters?, header: [String: String]?, httpMethod: HTTPMethod) throws -> URLRequest {
        let request = NSMutableURLRequest()
        request.httpMethod = httpMethod.rawValue
        
        guard let completeURL = self.completeURL(url: urlPath) else { throw GenericError.invalidURL }
        guard var urlComponents: URLComponents = URLComponents(url: completeURL, resolvingAgainstBaseURL: false) else { throw GenericError.invalidURL }
        
        //checking if parameters are needed
        if let params = parameters {
            //adding parameters to body
            if let bodyParameters = params.bodyParameters {
                request.httpBody = try JSONSerialization.data(withJSONObject: bodyParameters, options: [])
            }
            
            //adding parameters to query string
            if let queryParameters = params.queryParameters {
                let parametersItems: [String] = queryParameters.map({ (par) -> String in
                    var ret: String = ""
                    if let val = par.1 as? String {
                        let value = val != "" ? val : "null"
                        
                        ret = "\(par.0)=\(value)"
                    }
                    
                    return ret
                })
                
                urlComponents.query = parametersItems.joined(separator: "&")
            }
        }
        
        //setting url to request
        request.url = urlComponents.url
        request.cachePolicy = .reloadIgnoringLocalCacheData
        
        //adding HEAD parameters
        if header != nil {
            for parameter in header! {
                request.addValue(parameter.1, forHTTPHeaderField: parameter.0)
            }
        }
        
        return request as URLRequest
    }
    
    private func completeURL(url: String) -> URL? {
        if url.contains("http://") || url.contains("https://") {
            return URL(string: url)
        } else {
            return self.baseURL.appendingPathComponent(url)
        }
    }
}
