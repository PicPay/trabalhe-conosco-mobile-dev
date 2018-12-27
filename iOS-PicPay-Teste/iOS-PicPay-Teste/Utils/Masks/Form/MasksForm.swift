//
//  Masks.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 26/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import Foundation

//MARK:- Mask for credcard number
extension String {
    func maskNumberCred() -> String {
        let numberstr = self.removeCharacters()
        print(numberstr)
        var string = self
        let number = numberstr.count
        if number < 16 {
            if number % 4 == 0 {
                string.append(" ")
            }
        } else if number > 16 {
            string.removeLast()
        }
        return string
    }
    
    
    func maskDateExperence() -> String{
        let numberstr = self.removeCharacters()
        let number = numberstr.count
        var string = self
        if number < 5 {
            if number == 2 {
                string.append("/")
            }
        } else {
            string.removeLast()
        }
        return string
    }
}
