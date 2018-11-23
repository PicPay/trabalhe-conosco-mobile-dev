//
//  UserInteractor.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class UserInteractor {
    
    func list(success: @escaping (([User]) -> Void), failure:@escaping ((Error) -> Void)) {
        let service = UserService()
        
        RKLoading.showLoading()
        service.list { (data, response, error) in
            RKLoading.hideLoading()
            
            guard error == nil else {
                failure(error!)
                return
            }
            
            if let httpResponse = response as? HTTPURLResponse, !acceptableStatusCodes.contains(httpResponse.statusCode) {
                let error = ErrorHelper.createError(errorCode: httpResponse.statusCode, message: ErrorHelper.message.listUserError)
                failure(error)
            }
            
            do {
                let users = try JSONDecoder().decode([User].self, from: data!)
                success(users)
            } catch {
                let error = ErrorHelper.createError(errorCode: ErrorHelper.code.undefined, message: ErrorHelper.message.undefined)
                failure(error)
            }
        }
    }
    
}
