//
//  Session.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public typealias URLSessionCompletion = (() throws -> Data?) -> Void
public typealias URLSessionParameters = (bodyParameters: [String: AnyObject]?, queryParameters: [String: AnyObject]?)
public typealias SessionItemsCompletion = (SessionResult) -> Void

public enum SessionResult {
    case success(Data)
    case failure(Error)
}

class Session: URLSessionBase {
    override init(session: URLSession, baseURL: URL) {
        super.init(session: session, baseURL: baseURL)
    }
    
    // MARK: - HTTP Verbs
    @discardableResult public func GET(url: String, parameters: URLSessionParameters?, header: [String: String]?, completion: @escaping URLSessionCompletion) -> URLSessionTask? {
        return self.dataTaskFor(httpMethod: .get, url: url, parameters: parameters, header: header, completion: completion)
    }
    
    @discardableResult public func POST(url: String, parameters: URLSessionParameters?, header: [String: String]?, completion: @escaping URLSessionCompletion) -> URLSessionTask? {
        return self.dataTaskFor(httpMethod: .post, url: url, parameters: parameters, header: header, completion: completion)
    }
    
    @discardableResult public func HEAD(url: String, parameters: URLSessionParameters?, header: [String: String]?, completion: @escaping URLSessionCompletion) -> URLSessionTask? {
        return self.dataTaskFor(httpMethod: .head, url: url, parameters: parameters, header: header, completion: completion)
    }
    
    @discardableResult public func PUT(url: String, parameters: URLSessionParameters?, header: [String: String], completion: @escaping URLSessionCompletion) -> URLSessionTask? {
        return self.dataTaskFor(httpMethod: .put, url: url, parameters: parameters, header: header, completion: completion)
    }
    
    @discardableResult public func DELETE(url: String, parameters: URLSessionParameters?, header: [String: String], completion: @escaping URLSessionCompletion) -> URLSessionTask? {
        return self.dataTaskFor(httpMethod: .delete, url: url, parameters: parameters, header: header, completion: completion)
    }
    
    @discardableResult public func PATCH(url: String, parameters: URLSessionParameters?, header: [String: String], completion: @escaping URLSessionCompletion) -> URLSessionTask? {
        return self.dataTaskFor(httpMethod: .patch, url: url, parameters: parameters, header: header, completion: completion)
    }
    
    // MARK: - Private methods
    
    private func dataTaskFor(httpMethod: HTTPMethod, url: String, parameters: URLSessionParameters?, header: [String: String]?, completion: @escaping URLSessionCompletion) -> URLSessionTask? {
        do {
            let request = try self.request(urlPath: url, parameters: parameters, header: header, httpMethod: httpMethod)
            
            let sessionTask: URLSessionTask = self.session.dataTask(with: request, completionHandler: {(data, response, error) -> Void  in
                self.completionHandler(httpMethod: httpMethod, data: data, response: response, error: error, completion: completion)
            })
            
            sessionTask.resume()
            
            return sessionTask
        } catch let errorRequest {
            OperationQueue.main.addOperation {
                completion { throw errorRequest }
            }
        }
        
        return nil
    }
    
    private func completionHandler(httpMethod: HTTPMethod, data: Data?, response: URLResponse?, error: Error?, completion: @escaping URLSessionCompletion) {
        do {
            //check if there is no error
            if error != nil {
                throw error!
            }
            
            //unwraping httpResponse
            guard let httpResponse = response as? HTTPURLResponse else {
                throw GenericError.parse("The HTTPURLResponse could not be parsed")
            }
            
            //check if there is an httpStatus code ~= 200...299 (Success)
            if 200 ... 299 ~= httpResponse.statusCode {
                
                if httpMethod == .head {
                    OperationQueue.main.addOperation {
                        //success
                        completion { data }
                    }
                } else {
                    //trying to get the data
                    guard let responseData = data else {
                        throw GenericError.parse("Problems on parsing Data from request: \(String(describing: httpResponse.url))")
                    }
                    
                    OperationQueue.main.addOperation {
                        //success
                        completion { responseData }
                    }
                }
                
            } else {
                //checking status of http
                throw URLSessionError.httpError(httpResponse.statusCode)
            }
        } catch let errorCallback {
            OperationQueue.main.addOperation {
                completion { throw errorCallback }
            }
        }
    }
}
