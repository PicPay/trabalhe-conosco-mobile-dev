//
//  DateExtensions.swift
//  Desafio-Mobile-PicPay
//
//  Created by Luiz Hammerli on 29/07/2018.
//  Copyright © 2018 Luiz Hammerli. All rights reserved.
//

import Foundation

extension Date {
    func timeAgoDisplay() -> String {
        let secondsAgo = Int(Date().timeIntervalSince(self))
        
        let minute = 60
        let hour = 60 * minute
        let day = 24 * hour
        let week = 7 * day
        let month = 4 * week
        
        let quotient: Int
        let unit: String
        if secondsAgo < minute {
            quotient = secondsAgo
            unit = "segundo"
        } else if secondsAgo < hour {
            quotient = secondsAgo / minute
            unit = "minuto"
        } else if secondsAgo < day {
            quotient = secondsAgo / hour
            unit = "hora"
        } else if secondsAgo < week {
            quotient = secondsAgo / day
            unit = "dia"
        } else if secondsAgo < month {
            quotient = secondsAgo / week
            unit = "semana"
        } else {
            quotient = secondsAgo / month
            unit = "mes"
        }
        
        if(unit == "mes"){
            return "\(quotient) \(unit)\(quotient == 1 ? "" : "es") atrás"
        }
        
        return "\(quotient) \(unit)\(quotient == 1 ? "" : "s") atrás"
    }
}
