//
//  ExDouble.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 14/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import Foundation

extension Double {
    func formataValorMonetario() -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = Locale(identifier: "pt_BR")
        formatter.currencyCode = "R$ "
        
        let valorFormatar = NSNumber(value: self as Double)
        if let valor = formatter.string(from: valorFormatar) {
            return valor
        }
        
        return ""
    }
}
