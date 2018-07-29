//
//  apiCliente+PicPayApiConvenienceMethods.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 25/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import Foundation

extension ApiClient{
    
    func fetchUserContacts(completion: @escaping ([Contact]?, String?)->Void){
        
        self.taskForGETMethod(stringURL: ClientStrings.picPayApiURL+ClientStrings.picPayApiUsersMethod) { (data, message) in            
            if let data = data{
                do{
                    let contacts = try JSONDecoder().decode([Contact].self, from: data)
                    completion(contacts, nil)
                    return
                }catch let error{
                    print(error)
                }
                completion(nil, message)
            }
        }
    }
    
    func postPaymentData(card: Card, value: Double, userID: Int, completion: @escaping (Bool, String?)->Void){
        guard let cardNumber = card.card_number, let cardCvv = card.cvv, let cardDate = card.expiry_date else {return}        
        
        if(cardNumber != "1111111111111111"){
            completion(false, "Cartão inválido")
            return
        }
        
        let jsonBody: String = "{\"card_number\": \"\(cardNumber)\", \"cvv\": \(cardCvv), \"value\": \(value), \"expiry_date\": \"\(cardDate)\", \"destination_user_id\": \(userID)}"
        
        self.taskForPOSTMethod(stringURL: ClientStrings.picPayApiURL+ClientStrings.picPayApiPostPaymentMethod, jsonBody: jsonBody) { (data, message) in
            if let data = data{
                guard let jsonTransactionApiObject = try? JSONDecoder().decode(JsonTransactionApiObject.self, from: data) else {return}
                DispatchQueue.main.async {
                    if(jsonTransactionApiObject.transaction.status == "Aprovada"){
                        self.appDelegate.coreDataManager.saveTransaction(jsonTransactionApiObject)
                        completion(true, message)
                        return
                    }
                    completion(false, message)
                }
            }else{
                DispatchQueue.main.async {
                    completion(false, message)
                }
            }
        }
    }
}
