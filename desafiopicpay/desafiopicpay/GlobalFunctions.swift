//
//  GlobalFunctions.swift
//  desafiopicpay
//
//  Created by Rodrigo Kieffer on 21/11/18.
//  Copyright © 2018 Rodrigo Kieffer. All rights reserved.
//

import UIKit

// Fonts

let helveticaNeueMedium = "HelveticaNeue-Medium"

// Currency Formatter
let hundred = NSDecimalNumber(string: "100")
let currencyValidationMaxLength = "R$###.###.###,##".length

// Colors

let greenColor = UIColor(hex: 0x20C25E, alpha: 1.0)
let greyColor = UIColor(hex: 0x707070, alpha: 1.0)
let whiteLowOpacity = UIColor(hex: 0xFFFFFF, alpha: 0.1)


// MARK: - Generic functions

func corner(in view: UIView, radius: Float) {
    view.layer.cornerRadius = CGFloat(radius)
}

func removeFormatterFromNumbers(stringFormatted: String) -> String {
    let pattern = "[^\\d]"
    
    do {
        let regex = try NSRegularExpression(pattern: pattern, options: NSRegularExpression.Options.caseInsensitive)
        let stringWithoutFormatter = regex.stringByReplacingMatches(in: stringFormatted, options: NSRegularExpression.MatchingOptions(),range: NSMakeRange(0, stringFormatted.length), withTemplate:"")
        
        return stringWithoutFormatter
    } catch {
        debugPrint(error)
        return stringFormatted
    }
}

// MARK: - HTTP

var acceptableStatusCodes: [Int] { return Array(200..<300) }

struct HTTPMethods {
    static let get = "GET"
    static let post = "POST"
    static let put = "PUT"
    static let delete = "DELETE"
}
