//
//  ExString.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 14/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import Foundation

extension String {
    
    func removeFormatacao() -> String {
        
        // return self.replacingOccurrences(of: "\\D", with: "", options: .regularExpression, range: self.indices)
        return self.replacingOccurrences(of: "\\D", with: "", options: .regularExpression, range: nil)
        
    }
    
    func currencyInputFormatting() -> String {
        
        var number: NSNumber!
        let formatter = NumberFormatter()
        formatter.locale = Locale(identifier: "pt_BR") // Todos os valores trabalhados no guia é no Brasil.
        if #available(iOS 9.0, *) {
            formatter.numberStyle = .currencyAccounting
        } else {
            formatter.numberStyle = .currency
        }
        
        formatter.currencySymbol = "R$"
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 2
        
        var amountWithPrefix = self
        
        // remove from String: "$", ".", ","
        let regex = try! NSRegularExpression(pattern: "[^0-9]", options: .caseInsensitive)
        amountWithPrefix = regex.stringByReplacingMatches(in: amountWithPrefix, options: NSRegularExpression.MatchingOptions(rawValue: 0), range: NSMakeRange(0, self.characters.count), withTemplate: "")
        
        let double = (amountWithPrefix as NSString).doubleValue
        number = NSNumber(value: (double / 100))
        
        // if first number is 0 or all numbers were deleted
        guard number != 0 as NSNumber else {
            return ""
        }
        
        let textoFormatado = formatter.string(from: number)!
        return textoFormatado
    }
    
    func removeFormatacaoMonetaria() -> String {
        var aux = ""
        aux = self.replacingOccurrences(of: "R$", with: "")
//        aux = aux.replacingOccurrences(of: " ", with: "")
        aux = aux.replacingOccurrences(of: ",", with: ".")
        aux = aux.trimmingCharacters(in: .whitespacesAndNewlines)
        return aux
    }
}
