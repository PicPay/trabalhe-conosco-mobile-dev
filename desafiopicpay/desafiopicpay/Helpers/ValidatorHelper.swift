//
//  ValidatorHelper.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 23/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import Foundation

class ValidatorHelper {
    
    // MARK: - Validators
    
    static func string(_ text: String, min: Int = 0, max: Int = 0, numeric: Bool = false) -> Bool {
        var string = text.trim()
        if numeric { string = removeFormatterFromNumbers(stringFormatted: text) }
        if string.length < min { return false }
        if max > 0 && string.length > max { return false }
        
        return true
    }
    
    static func date(_ text: String, format: String = "dd/MM/yyyy") -> Bool {
        let date = stringToDate(text, format: format)
        guard date != nil else {
            return false
        }
        
        return true
    }
    
    static func dateIsGreaterThanToday(_ text: String, format: String = "dd/MM/yyyy") -> Bool {
        guard date(text, format: format) else {
            return false
        }
        
        let expiry = stringToDate(text, format: format)
        let today = Date()
        guard expiry! > today else {
            return false
        }
        
        return true
    }
    
    static func birthdate(_ text: String, format: String = "dd/MM/yyyy") -> Bool {
        guard date(text, format: format) else {
            return false
        }
        
        let birthdate = stringToDate(text, format: format)
        let today = Date()
        
        return (birthdate?.compare(today) == .orderedAscending)
    }
    
    static func password(_ text: String, min: Int = 4, max: Int = 4, numeric: Bool = true) -> Bool {
        var string = text.trim()
        if numeric { string = removeFormatterFromNumbers(stringFormatted: text) }
        if string.length < min { return false }
        
        let pattern = "(?!.*([0-9])\\1{3})(?=.*\\d{4})[0-9]+"
        do {
            let regex = try NSRegularExpression(pattern: pattern, options: NSRegularExpression.Options.caseInsensitive)
            let range = NSRange(location: 0, length: text.length)
            let matches = regex.matches(in: text, options: [], range: range)
            
            if matches.count > 0 {
                if !isSequential(text) {
                    return true
                }
            }
            
            return false
        } catch {
            debugPrint(error)
            return false
        }
    }
    
    // MARK: - Privates
    
    private static func stringToDate(_ text: String, format: String = "dd/MM/yyyy") -> Date? {
        var newDate = text
        var newFormat = format
        
        if !format.contains("HH:mm") {
            newDate = "\(text) 12:00"
            newFormat = "\(format) HH:mm"
        }
        
        let formatter = DateFormatter()
        formatter.locale = Locale(identifier: "pt_BR")
        formatter.timeZone = TimeZone(abbreviation: "UTC")
        formatter.dateFormat = newFormat
        
        let date = formatter.date(from: newDate)
        return date
    }
    
    private static func isSequential(_ text: String) -> Bool {
        var sequential = true
        var lastNumber = -1
        for char in text {
            let number = Int(String(char))
            
            if lastNumber == -1 || lastNumber == number! - 1 || lastNumber == number! + 1 {
                lastNumber = number!
            } else {
                sequential = false
                break
            }
        }
        
        return sequential
    }
    
}
