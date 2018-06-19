//
//  Card.swift
//  Payment
//
//  Created by Carlos Alexandre Moscoso on 11/06/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation

struct Card: Codable {
    static func isValidNumber(_ number: String) -> Bool {
        return number.count == 16 &&
            number.rangeOfCharacter(from: CharacterSet.decimalDigits.inverted) == nil
    }
    
    static func isValidSecurityCode(_ code: String) -> Bool {
        return code.count == 3 &&
            code.rangeOfCharacter(from: CharacterSet.decimalDigits.inverted) == nil
    }
    
    static func isValidDate(_ date: String) -> Bool {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/yyyy"
        
        return dateFormatter.date(from: date) != nil
    }
    
    static func isValidDescription(_ description: String) -> Bool {
        return !description.isEmpty
    }
    
    let id: String
    let number: String
    let expires: String
    let description: String
    let securityCode: String
}
