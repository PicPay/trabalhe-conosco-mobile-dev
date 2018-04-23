//
//  UserEndPoint.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

enum NetworkEnvironment {
    case test
}

enum UserApi {
    case list
    case makeTransaction(userId: Int, value: Double, craditCard: CreditCard)
}

extension UserApi: EndPointType {
    
    var environmentBaseURL : String {
        return "http://careers.picpay.com/tests/mobdev/"
    }
    
    var baseURL: URL {
        guard let url = URL(string: environmentBaseURL) else { fatalError("baseURL could not be configured.")}
        return url
    }
    
    var path: String {
        switch self {
        case .list:
            return "users"
        case .makeTransaction( _, _, _):
            return "transaction"
        }
    }
    
    var httpMethod: HTTPMethod {
        switch self {
        case .list:
            return .get
        case .makeTransaction(_, _, _):
            return .post
        }
    }
    
    var task: HTTPTask {
        switch self {
        case .makeTransaction(let userId, let value, let creditCard):
            
            var parameters: Parameters = [:]
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "MM/yy"
            parameters["card_number"] = creditCard.cardNumber
            parameters["cvv"] = creditCard.cvv
            parameters["expiry_date"] = dateFormatter.string(from: creditCard.expireDate)
            parameters["value"] = value
            parameters["destination_user_id"] = userId
            
            return .requestParameters(bodyParameters: parameters, bodyEncoding: .jsonEncoding, urlParameters: nil)
        case .list:
            return .requestParameters(bodyParameters: nil, bodyEncoding: .urlEncoding, urlParameters: nil)
        }
    }
    
    var headers: HTTPHeaders? {
        return nil
    }
    
}
