//
//  TicketModelCredCard.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 08/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import Foundation

//Cell 1
struct TicketViewModelCredCard {
    let value_pay: Float
    
    let total: String
    let numero: String
    
    init(pay: Payment) {
        self.value_pay = pay.value_pay
        
        let cerd = CredCoreData()
        let credcard = cerd.FetchRequest()
        let num = credcard?.numero ?? ""
        
        let final = num.suffix(4)
        self.numero = "MasterCard " + final
        
        self.total = "R$ \(self.value_pay)".replacingOccurrences(of: ".", with: ",")
    }
}
