//
//  UserDto.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 27/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import ObjectMapper
import Alamofire
import AlamofireObjectMapper

class UserDto {
    
    func getUsersApi(onSuccess: @escaping (_ response: HTTPURLResponse, _ myObject: [UserProfile]?) -> Void,
                     onFail: @escaping (_ response: HTTPURLResponse?, _ erroDescription: String) -> Void) {

        // Obs: A better solution is get the API URL from a config file
        // Get users from API
        Alamofire.request("http://careers.picpay.com/tests/mobdev/users", method: .get).responseArray { (response: DataResponse<[UserProfile]>) in
            
            switch response.result {
            case .success:
                
                if let objeto = response.result.value {
                    onSuccess(response.response!, objeto)
                } else {
                    onFail(response.response!, "Sem resposta do servidor no momento. Tente novamente")
                }
                
            case .failure(let error):
                
                let mensagemErro = error.localizedDescription
                onFail(response.response, mensagemErro)
                
            }
        }
    }
}
