//
//  UserManager.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class UsersManager: NSObject {
    fileprivate let business: UsersBusiness = UsersBusiness()
    
    public func fetch(completion: @escaping (ApiResult<[User]>) -> Void) {
        OperationQueue.main.addOperation { [weak self] in
            self?.business.fetch(completion: completion)
        }
    }
}
