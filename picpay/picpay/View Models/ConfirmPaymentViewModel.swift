//
//  ConfirmPaymentViewModel.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation

class ConfirmPaymentViewModel {
    
    let user: User
    var value: Double?
    var transaction: Transaction?
    
    init(with user: User) {
        self.user = user
    }
    
    func confirmPaymment(onSuccess: @escaping () -> Void, onError: @escaping (String) -> Void) {
        guard let value = self.value else {
            onError("Campo de valor é obrigatorio")
            return
        }
        guard let data = Keychain.getData(key: KeysConstant.creditCardKey) else {
            onError("Não foi possível carregar as informações do cartão de crédito cadastrado")
            return
        }
        do {
            let creditCard = try JSONDecoder().decode(CreditCard.self, from: data)
            NetworkManager.shared.makeTransaction(userId: user.id, value: value, creditCard: creditCard) { (transaction, message) in
                guard let transaction = transaction else {
                    guard let message = message else {
                        onError("Ocorreu um erro")
                        return
                    }
                    onError(message)
                    return
                }
                if transaction.success {
                    self.transaction = transaction
                    onSuccess()
                } else {
                    onError("Transação " + transaction.status)
                }
            }
        } catch {
            onError("Não foi possível carregar as informações do cartão de crédito cadastrado")
            return
        }
    }
    
}
