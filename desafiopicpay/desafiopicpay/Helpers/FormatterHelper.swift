//
//  FormatterHelper.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class FormatterHelper {
    
    static func stringFromDate(date: Date?, pattern: String?) -> String {
        guard date != nil else {
            return ""
        }
        
        let formatter = DateFormatter()
        
        formatter.dateFormat = pattern
        formatter.locale = Locale(identifier: "pt_BR")
        formatter.timeZone = TimeZone(abbreviation: "UTC")
        if pattern == nil {
            formatter.dateFormat = "dd/MM/yyyy"
        }
        
        return formatter.string(from: date!)
    }
    
    static func dateFromString(string: String, pattern: String?) -> Date? {
        let formatter = DateFormatter()
        
        formatter.dateFormat = pattern
        formatter.locale = Locale(identifier: "pt_BR")
        formatter.timeZone = TimeZone(abbreviation: "UTC")
        if pattern == nil {
            formatter.dateFormat = "dd/MM/yyyy"
        }
        
        return formatter.date(from: string)
    }
        
    static func formatCurrency(value: NSNumber) -> String {
        let numberFormatter = NumberFormatter()
        numberFormatter.numberStyle = NumberFormatter.Style.currency
        numberFormatter.currencyDecimalSeparator = ","
        numberFormatter.currencyGroupingSeparator = "."
        numberFormatter.currencySymbol = "R$"
        
        let formatted = numberFormatter.string(from: value)!
        return formatted
    }
    
    static func formatCurrency(value: Int) -> String {
        let decimalFormat = NSDecimalNumber(value: value).dividing(by: hundred)
        
        return formatCurrency(value: decimalFormat)
    }
    
}
