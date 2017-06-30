//
//  CardInfo.swift
//  SendMoney
//
//  Created by Jeferson Nazario on 28/06/17.
//  Copyright © 2017 jnazario.com. All rights reserved.
//

import Foundation
import RealmSwift

class CardInfo: Object {
    dynamic var id: Int = 0
    
    dynamic var labelCardInfo: String = ""
    dynamic var valueCardInfo: String = ""
    dynamic var keyboardType: UIKeyboardType = .default
    dynamic var placeholder: String = ""
    
    override static func primaryKey() -> String? {
        return "id"
    }
    
    static func getItemCard() -> [CardInfo] {
        var cardInfos = [CardInfo]()
        
        let cardNumber = CardInfo()
        cardNumber.labelCardInfo = Localize.CardNumber.rawValue.localized
        cardNumber.keyboardType = .numberPad
        cardNumber.placeholder = Localize.CardNumber.rawValue.localized
        cardInfos.append(cardNumber)
        
        let expiryDate = CardInfo()
        expiryDate.labelCardInfo = Localize.ExpiryDate.rawValue.localized
        expiryDate.keyboardType = .numbersAndPunctuation
        expiryDate.placeholder = "mm/aaaa"
        cardInfos.append(expiryDate)
        
        let cvv = CardInfo()
        cvv.labelCardInfo = Localize.Cvv.rawValue.localized
        cvv.keyboardType = .numberPad
        cvv.placeholder = Localize.SecurityCode.rawValue.localized
        cardInfos.append(cvv)
        
        return cardInfos
    }

}
