//
//  CredCardViewModel.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 07/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import Foundation


struct CredCardViewModel {
    let cvv: String
    let nome: String
    let numero: String
    let vencimento: String
    
    init(credcard: CredCard) {
        self.cvv = credcard.cvv!
        self.nome = credcard.nome!
        self.numero = credcard.numero!
        self.vencimento = credcard.vencimento!
    }
}
