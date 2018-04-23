//
//  NetworkManager.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

enum NetworkResponse:String {
    case success
    case authenticationError = "Você não esta autorizado a fazer isso"
    case badRequest = "Erro no pedido de conexão"
    case outdated = "Requisição que foi feita esta desatualizada"
    case failed = "Falha na requisição."
    case noData = "Não há dados a serem retornado"
    case unableToDecode = "Não foi possível interpretar os dados"
}

enum Result<String> {
    case success
    case failure(String)
}

struct NetworkManager {
    
    static let shared: NetworkManager = NetworkManager()
    static let environment : NetworkEnvironment = .test
    
    let router = Router<UserApi>()
    
    
    /// Lista usuários
    ///
    /// - Parameter completion: bloco contendo lista de usuário ou mensagem de erro
    func getUsers(completion: @escaping ([User]?, String?) -> Void){
        router.request(.list) { data, response, error in
            if error != nil {
                completion(nil, "Please check your network connection.")
            }
            
            if let response = response as? HTTPURLResponse {
                let result = self.handleNetworkResponse(response)
                switch result {
                case .success:
                    guard let responseData = data else {
                        completion(nil, NetworkResponse.noData.rawValue)
                        return
                    }
                    do {
                        let apiResponse = try JSONDecoder().decode([User].self, from: responseData)
                        completion(apiResponse, nil)
                    }catch {
                        print(error)
                        completion(nil, NetworkResponse.unableToDecode.rawValue)
                    }
                case .failure(let networkFailureError):
                    completion(nil, networkFailureError)
                }
            }
        }
    }
    
    func makeTransaction(userId: Int, value: Double, creditCard: CreditCard, completion: @escaping (Transaction?, String?) -> Void) {
        router.request(.makeTransaction(userId: userId, value: value, craditCard: creditCard)) { (data, response, error) in
            if error != nil {
                completion(nil, "Please check your network connection.")
            }
            
            if let response = response as? HTTPURLResponse {
                let result = self.handleNetworkResponse(response)
                switch result {
                case .success:
                    guard let responseData = data else {
                        completion(nil, NetworkResponse.noData.rawValue)
                        return
                    }
                    do {
                        let apiResponse = try JSONDecoder().decode(TransactionResponse.self, from: responseData)
                        completion(apiResponse.transaction, nil)
                    }catch {
                        print(error)
                        completion(nil, NetworkResponse.unableToDecode.rawValue)
                    }
                case .failure(let networkFailureError):
                    completion(nil, networkFailureError)
                }
            }
        }
    }
    
    fileprivate func handleNetworkResponse(_ response: HTTPURLResponse) -> Result<String>{
        switch response.statusCode {
        case 200...299: return .success
        case 401...500: return .failure(NetworkResponse.authenticationError.rawValue)
        case 501...599: return .failure(NetworkResponse.badRequest.rawValue)
        case 600: return .failure(NetworkResponse.outdated.rawValue)
        default: return .failure(NetworkResponse.failed.rawValue)
        }
    }
}
