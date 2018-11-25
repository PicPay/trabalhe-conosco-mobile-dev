//
//  UsersProvider.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation
import Reachability

public final class UsersProvider: UsersProviderProtocol {
    
    public func fetch(completion: @escaping (ApiResult<Data>) -> Void) {
        guard Utils.hasConnection else {
            completion(.failure(GenericError.parse("Sem conexão, tente mais tarde")))
            return
        }
        
        Api.users.GET(url: "/users", parameters: nil, header: nil) { result in
            do {
                let data = try result()
                if let response = data {
                    completion(.success(response))
                }
            } catch {
                completion(.failure(error))
            }
        }
    }

}
