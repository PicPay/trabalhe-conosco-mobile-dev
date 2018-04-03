//
//  Strings+Localized.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 27/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation

extension String {
    var localized: String {
        return NSLocalizedString(self, comment:"")
    }
    
    func currencyInputFormatting() -> String {
        
        var number: NSNumber!
        let formatter = NumberFormatter()
        formatter.numberStyle = .currencyAccounting
        formatter.currencySymbol = ""
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
        
        return formatter.string(from: number)!
    }
}

enum Localize: String {
    
    case SendMoney = "SendMoney"
    case MyTransfers = "MyTransfers"
    case TitleSelectUsers = "TitleSelectUsers"
    case NewTransfer = "NewTransfer"
    case Currency = "Currency"
    case CardInfo = "CardInfo"
    case CardNumber = "CardNumber"
    case ExpiryDate = "ExpiryDate"
    case Cvv = "Cvv"
    case SecurityCode = "SecurityCode"
    case Done = "Done"
    case RequiredFields = "RequiredFields"
    case PayTo = "PayTo"
    case TransactionSentSuccess = "TransactionSentSuccess"
    case MyCards = "MyCards"
    case NewCardSuccess = "NewCardSuccess"
    case CardNumberEncripted = "CardNumberEncripted"
    case PayWith = "PayWith"
    case AddNewCard = "AddNewCard"
    case PostError = "PostError"
    case PutValue = "PutValue"
    case YouPaid = "YouPaid"
    case NoContentTransfer = "NoContentTransfer"
    case NoContentCards = "NoContentCards"
}
