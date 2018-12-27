//
//  TicketViewModel.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 07/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit
import SDWebImage

struct TicketViewModel {
    let username: String
    let img: String
    let id: Int
    let data: Int
    let transacao: String
    
    let imgView = UIImageView()
    let dateString: String
    
    //credcard
    let value_pay: String
    let numero: String
    let valuestr: String
    
    let labeltotal: String
    
    init(ticket: TicketUser) {
        self.username = ticket.transaction.destination_user.username
        self.id = ticket.transaction.destination_user.id
        self.img = ticket.transaction.destination_user.img
        self.data = ticket.transaction.timestamp
        
        self.transacao = "Transação: \(ticket.transaction.id)"
        
        //User
        
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
        
        //CredCard
        self.value_pay = String(ticket.transaction.value)
        
        let cerd = CredCoreData()
        let credcard = cerd.FetchRequest()
        let num = credcard?.numero ?? ""
        
        let final = num.suffix(4)
        self.numero = "MasterCard " + final
        
        //Total
        self.valuestr = "R$ " + String(self.value_pay).replacingOccurrences(of: ".", with: ",")
        self.labeltotal = "Total pago"
    }
}
