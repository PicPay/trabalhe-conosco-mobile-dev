//
//  CreditCardViewModel.swift
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

import Foundation
import UIKit

enum FormCreditCardError: Error {
    case numberIsRequired
    case cvvIsRequired
    case expiredDateIsRequired
    case invalidExpiredDate
    case notSaveData
    case noData
}

class CreditCardViewModel {
    
    var number: Int64?
    var cvv: Int?
    var expiredDate: String?
    
    init() {
        try? getCreditCard()
    }
    
    /// Salva dados do cartão de crédito no keychain, foi optado por gravar no keychain
    /// porque é um dado muito importante e precisa de segurança em cima dele, sendo assim
    /// é melhor usar a camada de segurança da Apple do que usar um core data ou UserDefault
    /// ou SQL ou Realm por exemplo, outros
    ///
    /// - Throws: exception
    func saveAndValidationForm() throws -> Bool {
        guard let number: Int64 = self.number, number != 0 else {
            throw FormCreditCardError.numberIsRequired
        }
        guard let expiredDate: String = self.expiredDate, !expiredDate.isEmpty else {
            throw FormCreditCardError.expiredDateIsRequired
        }
        guard let cvv: Int = self.cvv, cvv != 0 else {
            throw FormCreditCardError.cvvIsRequired
        }
        
        let _ = Keychain.delete(key: KeysConstant.creditCardKey)
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/yy"
        guard let date = dateFormatter.date(from: expiredDate) else {
            throw FormCreditCardError.invalidExpiredDate
        }
        let creditCard = CreditCard(cardNumber: number, cvv: cvv, expireDate: date)
        let data = try JSONEncoder().encode(creditCard)
        if !Keychain.set(key: KeysConstant.creditCardKey, value: data) {
            throw FormCreditCardError.notSaveData
        }
        return true
    }
    
    func getCreditCard() throws {
        do {
            guard let data = Keychain.getData(key: KeysConstant.creditCardKey) else {
                throw FormCreditCardError.noData
            }
            let creditCard = try JSONDecoder().decode(CreditCard.self, from: data)
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "MM/yy"
            self.cvv = creditCard.cvv
            self.number = creditCard.cardNumber
            self.expiredDate = dateFormatter.string(from: creditCard.expireDate)
        } catch {
            throw FormCreditCardError.noData
        }
    }
    
    /// Retorna o texto do campo com a mascara
    ///
    /// - Parameters:
    ///   - textField: textFiled do delegate
    ///   - string: string de conteudo do textFiled
    ///   - mascara: mascara que vai representar
    ///   - range: range da string
    /// - Returns: String formatada
    func maskForTextField(textField: UITextField, string: String, mask: String, range: NSRange) -> String {
        var changedString = (textField.text as NSString?)?.replacingCharacters(in: range, with: string) ?? string
        if range.length == 1 &&
            string.count < range.length &&
            (textField.text as NSString?)?.substring(with: range).rangeOfCharacter(from: CharacterSet.init(charactersIn: "123456789")) == nil {
            
            var location = range.location
            let digitSet = CharacterSet.decimalDigits
            
            if location > 0 {
                for ch in changedString.unicodeScalars.reversed() {
                    if digitSet.contains(ch) {
                        break
                    }
                    location = location - 1
                }
            }
            
            changedString = (changedString as NSString).substring(to: location)
            return changedString
        }
        return Util.formatString(changedString, withFormat: mask)
    }
}
