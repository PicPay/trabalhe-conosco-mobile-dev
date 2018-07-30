//
//  StringExtensions.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 28/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import Foundation


extension String{
    func setValueMask()->String{
        var auxValue = self
        if(auxValue.count == 1){
            return "0,0\(self)"
        }
        
        if(auxValue.count == 2){
            return "0,\(self)"
        }
        
        let finalValues = auxValue.suffix(2)
        auxValue.removeLast()
        auxValue.removeLast()
        return "\(auxValue),\(finalValues)"
    }
    
    func getDoubleValue()->Double{
        var auxString = self
        var finalValue: String.SubSequence = ""
        
        if(self.count >= 3){
            finalValue = auxString.suffix(2)
            auxString.removeLast()
            auxString.removeLast()
            auxString.append(".\(finalValue)")
        }else{
            auxString = "0.\(self)"
        }
        
        return Double(auxString) ?? 0.0
    }
}

extension Double{
    func doubleToMoneyMask()->String{
        var stringvValue = String(self)
        let split = stringvValue.split(separator: ".")
        if split.count > 1 && split[1].count == 1 {
            stringvValue.append("0")
        }
        return stringvValue.replacingOccurrences(of: ".", with: ",")
    }
}
