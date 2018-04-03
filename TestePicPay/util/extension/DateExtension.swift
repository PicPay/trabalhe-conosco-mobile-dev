//
//  DateExtension.swift
//  TestePicPay
//
//  Created by Rodolfo Gusson on 30/03/18.
//  Copyright © 2018 PicPay. All rights reserved.
//

import Foundation
extension Date {
    static func fromExpire(_ string: String) -> Date?{
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yyyy"
        let monthAndYear = string.elementsFromExpiryDate()
        return formatter.date(from: "\(01)/\(monthAndYear.0)/\(monthAndYear.1)")
    }
}
