//
//  UsersBusiness.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class UsersBusiness {
    
    // Parameters
    fileprivate let provider: UsersProviderProtocol
    
    init() {
        provider = UsersProvider()
    }
    
    init(provider: UsersProviderProtocol) {
        self.provider = provider
    }
    
    public func fetch(completion: @escaping (ApiResult<[User]>) -> Void) {
        provider.fetch { result in
            switch result {
            case let .success(data):
                do {
                    let decoder = JSONDecoder()
                    let users = try decoder.decode([User].self, from: data)
                    
                    completion(.success(users))
                } catch let error as NSError {
                    completion(.failure(error))
                }
            case let .failure(error):
                completion(.failure(error))
            }
        }
    }}
