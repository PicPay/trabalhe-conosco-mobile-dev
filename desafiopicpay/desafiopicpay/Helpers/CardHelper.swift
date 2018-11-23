//
//  CardHelper.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 23/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class CardHelper {
    
    struct CardLength {
        static let Master       = 16
        static let Visa         = 16
        static let Amex         = 15
        static let Diners       = 14
        static let Hipercard    = 16
        static let Elo          = 16
        static let Default      = 16
    }
    
    private struct CardMatches {
        static let Amex         = ["37", "34"]
        static let Hipercard    = ["606282", "384100", "384140", "384160", "60", "62", "637"]
        static let Master       = ["5", "2"]
        static let Visa         = ["4"]
        static let Elo          = ["636368", "504175", "438935", "451416", "636297", "40117", "45763", "5067", "627780", "636", "431274", "457393", "506699", "509", "65"]
        static let Diners       = ["36"]
    }
    
    struct CardBrand {
        static let Master       = "MASTER"
        static let MasterCard   = "MASTERCARD"
        static let Visa         = "VISA"
        static let Hipercard    = "HIPERCARD"
        static let Elo          = "ELO"
        static let Diners       = "DINERS"
        static let Amex         = "AMEX"
        static let Alelo        = "ALELO"
    }

    static let validityLength = 4
    
    static func formatValidity(validity: String) -> String {
        //XX/XX
        
        if validity.length == 0 { return "" }
        
        var validityFormatted = removeFormatterFromNumbers(stringFormatted: validity)
        
        if validityFormatted.length > validityLength {
            let index = validityFormatted.index(validityFormatted.startIndex, offsetBy: validityLength)
            validityFormatted = validityFormatted.substring(to: index)
        }
        
        if validityFormatted.length <= 2 {
            validityFormatted = validityFormatted.replacingOccurrences(of: "(\\d{2})", with: "$1", options: String.CompareOptions.regularExpression, range: nil)
        } else {
            validityFormatted = validityFormatted.replacingOccurrences(of: "(\\d{2})(\\d+)", with: "$1/$2", options: String.CompareOptions.regularExpression, range: nil)
        }
        
        return validityFormatted
    }
    
    static func format(creditCardNumber: String!) -> String {
        if matchAmex(cardNumber: creditCardNumber) {
            return formatAmex(creditCardNumber: creditCardNumber)
        } else if matchDiners(cardNumber: creditCardNumber) {
            return formatDiners(creditCardNumber: creditCardNumber)
        } else {
            return formatVisaMasterAndOthers(creditCardNumber: creditCardNumber)
        }
    }
    
    static func formatWithBin(creditCardNumber: String!, bin: String!) -> String {
        if creditCardNumber.count <= bin.count {
            return format(creditCardNumber: bin)
        }
        if matchAmex(cardNumber: creditCardNumber) {
            return formatAmex(creditCardNumber: creditCardNumber)
        } else if matchDiners(cardNumber: creditCardNumber) {
            return formatDiners(creditCardNumber: creditCardNumber)
        } else {
            return formatVisaMasterAndOthers(creditCardNumber: creditCardNumber)
        }
    }
    
    static func formatVisaMasterAndOthers(creditCardNumber: String!) -> String {
        //XXXX XXXX XXXX XXXX
        if creditCardNumber == nil || creditCardNumber.length == 0 { return "" }
        
        var creditCardFormatted = removeFormatterFromNumbers(stringFormatted: creditCardNumber)
        
        if creditCardFormatted.length > CardLength.Default {
            let index = creditCardFormatted.index(creditCardFormatted.startIndex, offsetBy: CardLength.Default)
            creditCardFormatted = creditCardFormatted.substring(to: index)
        }
        
        if creditCardFormatted.length <= 4 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})", with: "$1", options: String.CompareOptions.regularExpression, range: nil)
        } else if creditCardFormatted.length <= 8 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})(\\d+)", with: "$1 $2", options: String.CompareOptions.regularExpression, range: nil)
        } else if creditCardFormatted.length <= 12 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})(\\d{4})(\\d+)", with: "$1 $2 $3", options: String.CompareOptions.regularExpression, range: nil)
        } else if creditCardFormatted.length <= 16 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})(\\d{4})(\\d{4})(\\d+)", with: "$1 $2 $3 $4", options: String.CompareOptions.regularExpression, range: nil)
        }
        
        return creditCardFormatted
    }
    
    static func formatDiners(creditCardNumber: String!) -> String {
        //XXXX XXXXXX XXXX
        if creditCardNumber == nil || creditCardNumber.length == 0 { return "" }
        
        var creditCardFormatted = removeFormatterFromNumbers(stringFormatted: creditCardNumber)
        
        if creditCardFormatted.length > CardLength.Diners {
            let index = creditCardFormatted.index(creditCardFormatted.startIndex, offsetBy: CardLength.Diners)
            creditCardFormatted = creditCardFormatted.substring(to: index)
        }
        
        if creditCardFormatted.length <= 4 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})", with: "$1", options: String.CompareOptions.regularExpression, range: nil)
        } else if creditCardFormatted.length <= 10 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})(\\d+)", with: "$1 $2", options: String.CompareOptions.regularExpression, range: nil)
        } else if creditCardFormatted.length <= 14 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})(\\d{6})(\\d+)", with: "$1 $2 $3", options: String.CompareOptions.regularExpression, range: nil)
        }
        
        return creditCardFormatted
    }
    
    static func formatAmex(creditCardNumber: String!) -> String {
        //XXXX XXXXXX XXXXX
        if creditCardNumber == nil || creditCardNumber.length == 0 { return "" }
        
        var creditCardFormatted = removeFormatterFromNumbers(stringFormatted: creditCardNumber)
        
        if creditCardFormatted.length > CardLength.Amex {
            let index = creditCardFormatted.index(creditCardFormatted.startIndex, offsetBy: CardLength.Amex)
            creditCardFormatted = creditCardFormatted.substring(to: index)
        }
        
        if creditCardFormatted.length <= 4 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})", with: "$1", options: String.CompareOptions.regularExpression, range: nil)
        } else if creditCardFormatted.length <= 10 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})(\\d+)", with: "$1 $2", options: String.CompareOptions.regularExpression, range: nil)
        } else if creditCardFormatted.length <= 15 {
            creditCardFormatted = creditCardFormatted.replacingOccurrences(of: "(\\d{4})(\\d{6})(\\d+)", with: "$1 $2 $3", options: String.CompareOptions.regularExpression, range: nil)
        }
        
        return creditCardFormatted
    }
    
    static func formatLastNumbersWithFullSize(lastNumbers: String!) -> String {
        return "•••• •••• •••• \(lastNumbers)"
    }
    
    static func matchVisa(cardNumber: String) -> Bool {
        return contains(string: cardNumber, in: CardMatches.Visa)
    }
    
    static func matchMaster(cardNumber: String) -> Bool {
        return contains(string: cardNumber, in: CardMatches.Master)
    }
    
    static func matchDiners(cardNumber: String) -> Bool {
        return contains(string: cardNumber, in: CardMatches.Diners)
    }
    
    static func matchAmex(cardNumber: String) -> Bool {
        return contains(string: cardNumber, in: CardMatches.Amex)
    }
    
    static func matchHipercard(cardNumber: String) -> Bool {
        return contains(string: cardNumber, in: CardMatches.Hipercard)
    }
    
    static func matchElo(cardNumber: String) -> Bool {
        return contains(string: cardNumber, in: CardMatches.Elo)
    }

    // MARK: - Privates
    
    private static func contains(string: String, in array: [String]) -> Bool {
        var contains = false
        for prefix in array {
            contains = string.hasPrefix(prefix)
            if contains {
                return contains
            }
        }
        
        return false
    }
    
}
