//
//  TicketViewModel.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 07/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import SDWebImage
import UIKit

//Cell 0
struct TicketViewModelUser {
    let username: String
    let img: String
    let id: Int
    let data: Int
    let transacao: String
    
    let imgView = UIImageView()
    let dateString: String
    
    init(contato: Contato, pay_id: Payment) {
        self.username = contato.username
        self.id = contato.id
        self.img = contato.img
        self.data = pay_id.timestamp
        self.transacao = "Transação: \(pay_id.id)"
        
        self.imgView.sd_setImage(with: URL(string: self.img), placeholderImage: UIImage(named: "noprofile.png"))
        
        let date = Date(timeIntervalSince1970: TimeInterval(self.data))
        let dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .short
        dateFormatter.dateFormat =  "dd/MM/yyyy"
        dateFormatter.locale = Locale(identifier: "pt_BR")
        let data = dateFormatter.string(from: date)
        dateFormatter.dateFormat = "HH:mm"
        let horas = dateFormatter.string(from: date)
        
        self.dateString = data + " às " + horas
        
    }
}


