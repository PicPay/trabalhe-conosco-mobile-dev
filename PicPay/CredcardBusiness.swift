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
        validate(card: card, with: accountStorage) { [weak self] result in
            guard let wSelf = self else { return }
            
            switch result {
            case .success:
                wSelf.setAsMainCard(card: card, with: accountStorage)
                accountStorage.account.cards.append(card)

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
    }
    
    fileprivate func validate(card: Card, with accountStorage: AccountStorage, completion: @escaping (Validate) -> Void) {
        var msgError = ""
        
        if card.number == 0 {
            msgError = "\(msgError)\u{2022} O número do cartão não foi informado\n"
        } else {
            let hasNumber = accountStorage.account.cards.filter { card.number == $0.number }.count
            if hasNumber > 0 {
                msgError = "\(msgError)\u{2022} O Cartão informado já foi cadastrado\n"
            }
        }
        
        if card.validate.isEmpty {
            msgError = "\(msgError)\u{2022} A validade do cartão não foi informada\n"
        }
        
        if card.cvv == 0 {
            msgError = "\(msgError)\u{2022} O número de segurança não foi informado\n"
        }
        
        if msgError.isEmpty {
            completion(.success)
        } else {
            completion(.failure(msgError))
        }
    }
}
