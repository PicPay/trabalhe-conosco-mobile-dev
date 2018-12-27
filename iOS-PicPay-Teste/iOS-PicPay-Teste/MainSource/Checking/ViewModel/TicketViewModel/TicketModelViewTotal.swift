//
//  TicketModelViewTotal.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 08/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import Foundation

//Cell 2
struct TicketViewModelTotal {
    let value_pay: String
    let lavel_total: String
    
    init(pay: Payment) {
        let value = "\(pay.value_pay)"
        self.value_pay = "R$ " + value.replacingOccurrences(of: ".", with: ",")
        self.lavel_total = "Total Pago"
    }
}
