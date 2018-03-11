//
//  Cartao.swift
//  PicPay
//
//  Created by Vinicius Alves on 10/03/2018.
//  Copyright © 2018 TIPiniquim. All rights reserved.
//

import Foundation

class Cartao: Codable {
    
    var card_number: String = ""
    var cvv: Int = -1
    var value: Double = -1.0
    var expiry_date: String = "00/00"
    var destination_user_id: Int = -1
}
