//
//  Masks.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 26/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import Foundation


//MARK:- Masks for payment
extension String {
    func maskOnTypingPayment() -> String {
        var strchg = self
        let num = strchg.count
        if strchg.first == "0" {
            strchg.removeFirst()
            let final = strchg.suffix(2)
            let inicio = strchg.prefix(1)
            var stringmask = inicio
            stringmask.append(",")
            stringmask = stringmask + final
            
            return String(stringmask)
        }
        let final = strchg.suffix(2)
        let inicio = strchg.prefix(num - 2)
        var stringmask = inicio
        stringmask.append(",")
        stringmask = stringmask + final
        
        return String(stringmask)
    }
    func removeMaskPayment() -> String {
        let num = self.count
        let removestr = self.prefix(num - 1)
        let numremove = removestr.count
        if numremove > 2 {
            let final = removestr.suffix(2)
            let inicio = removestr.prefix(numremove - 2)
            var stringmask = inicio
            stringmask.append(",")
            stringmask = stringmask + final
            
            return String(stringmask)
        } else {
            let str = self.removeCharacters().prefix(num - 1)
            let st = "0," + String(str)
            return st
        }
    }
    
    func removeCharacters() -> String {
        let string = String(self.filter { !"/ ,".contains($0) })
        return string
    }
}
