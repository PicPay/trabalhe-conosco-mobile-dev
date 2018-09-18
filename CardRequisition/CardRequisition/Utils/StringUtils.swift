//
//  StringUtils.swift
//  CardRequisition
//
//  Created by Raphael Carletti on 9/18/18.
//  Copyright © 2018 Raphael Carletti. All rights reserved.
//

import Foundation

class StringUtils {
    static func formatCreditCardNumber(text: String) -> String {
        let textReplaced = text.replacingOccurrences(of: " ", with: "")
        var newText = ""
        
        textReplaced.enumerated().forEach { (index, character) in
            if index % 4 == 0 && index > 0 {
                newText += " "
            }
            
            newText.append(character)
        }
        
        return newText
    }
    
    static func formatCreditCardNumberWithEncryption(cardNumber: String) -> String {
        let textReplaced = cardNumber.replacingOccurrences(of: " ", with: "")
        var newText = ""
        var numberOfSpaces = 0
        
        textReplaced.enumerated().forEach { (index, character) in
            if index % 4 == 0 && index > 0 {
                numberOfSpaces += 1
                newText += " "
            }
            
            if numberOfSpaces == 3 {
                newText.append(character)
            } else {
                newText.append("*")
            }
        }
        
        return newText
    }
}

extension String {
    func currencyInputFormatting() -> String? {
        
        var number: NSNumber?
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 2
        formatter.currencySymbol = ""
        
        var amountWithPrefix = self
        
        if let regex = try? NSRegularExpression(pattern: "[^0-9]", options: .caseInsensitive) {
            amountWithPrefix = regex.stringByReplacingMatches(in: amountWithPrefix, options: NSRegularExpression.MatchingOptions(rawValue: 0), range: NSMakeRange(0, self.count), withTemplate: "")
            
            let double = (amountWithPrefix as NSString).doubleValue
            number = NSNumber(value: (double / 100))
            
            if let number = number, let string = formatter.string(from: number) {
                return string
            }
        }
        
        return nil
    }
    
    func getDouble() -> Double? {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 2
        
        if let number = formatter.number(from: self)?.doubleValue {
            return number
        }
        
        return nil
    }
}
