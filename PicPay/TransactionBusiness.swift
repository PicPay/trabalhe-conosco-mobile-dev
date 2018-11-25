//
//  TransactionBusiness.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/24/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class TransactionBusiness {
    
    public enum ProcessStatus {
        case success
        case failure(Error)
    }
    
    public enum Validate {
        case success
        case failure(String)
    }

    // Parameters
    fileprivate let provider: TransactionProviderProtocol
    
    init() {
        provider = TransactionProvider()
    }
    
    init(provider: TransactionProviderProtocol) {
        self.provider = provider
    }
    
    public func process(payment transaction: Transaction, completion: @escaping (ProcessStatus) -> Void) {
        validate(transaction: transaction) { [weak self] result in
            guard let wSelf = self else { return }
            
            switch result {
            case .success:
                wSelf.provider.process(payment: transaction) { result in
                    switch result {
                    case let .success(data):
                        do {
                            let jsonResponse = try JSONSerialization.jsonObject(with: data, options: [])
                            if let json = jsonResponse as? JSON,
                                let transaction = json["transaction"] as? JSON,
                                let status = transaction["success"] as? Bool {
                                
                                if status {
                                    completion(.success)
                                } else {
                                    completion(.failure(GenericError.parse("Transação não aprovada")))
                                }
                            } else {
                                completion(.failure(GenericError.parse("Não foi possivel processar a transação")))
                            }
                        } catch let error as NSError {
                            completion(.failure(error))
                        }
                    case let .failure(error):
                        completion(.failure(error))
                    }
                }
            case let .failure(msgError):
                completion(.failure(GenericError.parse(msgError)))
            }
        }
    }
    
    fileprivate func validate(transaction: Transaction, completion: @escaping (Validate) -> Void) {
        var msgError = ""
        
        if transaction.value == 0 {
            msgError = "\(msgError)Informe o valor da transação\n"
        }
        
        if msgError.isEmpty {
            completion(.success)
        } else {
            completion(.failure(msgError))
        }
    }
}
