//
//  AlamofireService.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/17/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import Alamofire
import UIKit.UIImage

class AlamofireService {
    static func getAllUsers(completion: @escaping (_ success: Bool, _ users: [User]?)->()) {
        Alamofire.request("\(AlamofireConstants.baseUrl)\(AlamofireConstants.getUsersUrl)", method: .get).responseJSON { (response) in
            guard let data = response.result.value as? [[String: Any]] else {
                completion(false, nil)
                return
            }
            
            var users: [User] = []
            for user in data {
                if let user = User(dict: user) {
                    users.append(user)
                }
            }
            
            completion(true, users)
        }
    }
    
    static func payment(userId: Int, card: CardCoreData, cvv: Int, value: Double, completion: @escaping (_ success: Bool)->()) {
        let params: [String: Any] = [PaymentParams.cardNumber: card.cardNumber, PaymentParams.cvv: cvv, PaymentParams.expiryDate: card.expiryDate, PaymentParams.value: value, PaymentParams.destinationUserId: userId]
        
        Alamofire.request("\(AlamofireConstants.baseUrl)\(AlamofireConstants.postPayment)", method: .post, parameters: params, encoding: JSONEncoding.default).responseJSON { (response) in
            guard let data = response.result.value as? [String: Any], let transaction = data[AlamofireConstants.transaction] as? [String: Any], let success = transaction[AlamofireConstants.success] as? Bool else {
                completion(false)
                return
            }
            
            completion(success)
        }
    }
    
    static func downloadImage(imageUrl: String, completion: @escaping (_ success: Bool, _ image: UIImage?)->()) {
        Alamofire.request(imageUrl, method: .get).responseData { (response) in
            DispatchQueue.global().async {
                guard let data = response.result.value, let image = UIImage(data: data) else {
                    completion(false, nil)
                    return
                }
                
                completion(true, image)
            }
        }
    }
}
