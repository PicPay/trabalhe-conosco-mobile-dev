//
//  CredcardBusiness.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import Foundation

public final class CredcardBusiness {
    public enum Validate {
        case success
        case failure(String)
    }
    
    public func register(card: Card, with accountStorage: AccountStorage, completion: @escaping (DAOResult) -> Void) {
        accountStorage.account.cards.append(card)
        setAsMainCard(card: card, with: accountStorage)
        
        validate(card: card) { result in
            switch result {
            case .success:
                if accountStorage.saveChanges() {
                    completion(.success)
                } else {
                    completion(.failure(GenericError.parse("Não foi possível salvar o cartão informado")))
                }
            case let .failure(msgError):
                completion(.failure(GenericError.parse(msgError)))
            }
        }
    }
    
    public func setAsMainCard(card: Card, with accountStorage: AccountStorage) {
        accountStorage.account.cards = accountStorage.account.cards.map {
            let isMainCard = card.number == $0.number
            return Card(number: $0.number, validate: $0.validate, cvv: $0.cvv, isMainCard: isMainCard)
        }
        
        accountStorage.saveChanges()
    }
    
    fileprivate func validate(card: Card, completion: @escaping (Validate) -> Void) {
        var msgError = ""
        
        if card.number == 0 {
            msgError = "\(msgError)O número do cartão não foi informado\n"
        }
        
        if card.validate.isEmpty {
            msgError = "\(msgError)A validade do cartão não foi informada\n"
        }
        
        if card.cvv == 0 {
            msgError = "\(msgError)O número de segurança não foi informado\n"
        }
        
        if msgError.isEmpty {
            completion(.success)
        } else {
            completion(.failure(msgError))
        }
    }
}
