//
//  CreditCard.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 30/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

class CreditCard {
    var brand: CreditCardBrand?
    var name: String?
    var number: String?
    var expireDate: Date?
    var cvc: String?
    var cep: String?
    
    init(brand: CreditCardBrand, name: String, number: String, expireDate: Date, cvc: String, cep: String) {
        self.brand = brand
        self.name = name
        self.number = number
        self.expireDate = expireDate
        self.cvc = cvc
        self.cep = cep
    }
}

enum CreditCardBrand: String {
    case visa = "Visa"
    case mastercard = "Mastercard"
}
