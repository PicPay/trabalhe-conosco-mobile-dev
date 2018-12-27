//
//  TicketView.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 05/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class CheckingView: UIView {
    
    //User
    let imgavatar: UIImageView = {
        let image = UIImageView(image: #imageLiteral(resourceName: "noprofile"))
        image.layer.cornerRadius = 50
        image.layer.masksToBounds = true
        image.contentMode = .scaleAspectFill
        return image
    }()
    
    let username: UILabel = {
        let label = UILabel()
        label.font = UIFont.boldSystemFont(ofSize: 26)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        label.textAlignment = .center
        return label
    }()
    
    let data: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "HelveticaNeue-Thin", size: 16)
        label.minimumScaleFactor = 0.5
        label.textColor = .lightGray
        label.textAlignment = .center
        return label
    }()
    
    let transacao: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "HelveticaNeue-Thin", size: 14)
        label.minimumScaleFactor = 0.5
        label.textColor = .lightGray
        label.textAlignment = .center
        return label
    }()

    
    //Cartaro Credito
    let labelCredc: UILabel = {
        let label = UILabel()
        label.font = UIFont(name: "HelveticaNeue-Regular", size: 19)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        label.textAlignment = .left
        return label
    }()
    let labeltotalcred: UILabel = {
        let label = UILabel()
        label.font = UIFont.boldSystemFont(ofSize: 19)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        label.textAlignment = .right
        return label
    }()
    let stackView: UIStackView = {
        let stack = UIStackView()
        stack.axis = .horizontal
        stack.distribution = .fillProportionally
        return stack
    }()
    
    
    //Total
    let label: UILabel = {
        let label = UILabel()
        label.font = UIFont.boldSystemFont(ofSize: 22)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        label.textAlignment = .left
        return label
    }()
    let labeltotal: UILabel = {
        let label = UILabel()
        label.font = UIFont.boldSystemFont(ofSize: 22)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        label.textAlignment = .right
        return label
    }()
    let stackViewtotal: UIStackView = {
        let stack = UIStackView()
        stack.axis = .horizontal
        stack.distribution = .fillProportionally
        return stack
    }()
    
    
    let transactionStatus: UILabel = {
        let label = UILabel()
        label.font = UIFont.boldSystemFont(ofSize: 22)
        label.minimumScaleFactor = 0.5
        label.textColor = .white
        label.textAlignment = .right
        return label
    }()
    
    
    let line1 = LineView()
    let line2 = LineView()
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    
    
    var ticket: TicketViewModel! {
        didSet {
            self.imgavatar.image = ticket.imgView.image
            self.username.text = ticket.username
            self.data.text = ticket.dateString
            self.transacao.text = ticket.transacao
            
            self.labelCredc.text = ticket.numero
            self.labeltotalcred.text = ticket.valuestr
            
            self.label.text = ticket.labeltotal
            self.labeltotal.text = ticket.valuestr
        }
    }
    
    
    func configure() {
        self.addSubview(self.imgavatar)
        self.addSubview(self.username)
        self.addSubview(self.data)
        self.addSubview(self.transacao)
        self.addSubview(self.line1)
        self.addSubview(self.line2)
        
        self.addSubview(self.stackView)
        self.stackView.addArrangedSubview(self.labelCredc)
        self.stackView.addArrangedSubview(self.labeltotalcred)
        
        self.addSubview(self.stackViewtotal)
        self.stackViewtotal.addArrangedSubview(self.label)
        self.stackViewtotal.addArrangedSubview(self.labeltotal)
        
        self.imgavatar.anchorXY(centerX: self.centerXAnchor, centerY: nil, top: nil, leading: nil, bottom: self.username.topAnchor, trailing: nil, padding: .init(top: 35, left: 0, bottom: 0, right: 0), size: .init(width: 100, height: 100))
        
        self.username.anchor(top: nil, leading: self.leadingAnchor, bottom: self.data.topAnchor, trailing: self.trailingAnchor, padding: .init(top: 0, left: 25, bottom: 0, right: 25), size: .init(width: 0, height: 35))
        
        self.data.anchor(top: nil, leading: self.username.leadingAnchor, bottom: self.transacao.topAnchor, trailing: self.username.trailingAnchor, padding: .init(top: 5, left: 15, bottom: 0, right: 15), size: .init(width: 0, height: 25))
        
        self.transacao.anchor(top: nil, leading: self.data.leadingAnchor, bottom: self.centerYAnchor, trailing: self.data.trailingAnchor, padding: .init(top: 0, left: 15, bottom: 25, right: 15), size: .init(width: 0, height: 20))
        
        self.line1.anchorXY(centerX: nil, centerY: nil, top: self.centerYAnchor, leading: self.leadingAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(top: 0, left: 25, bottom: 0, right: 25), size: .init(width: 0, height: 1))
        
        self.stackView.anchor(top: self.line1.bottomAnchor, leading: self.leadingAnchor, bottom: nil, trailing: self.trailingAnchor, padding: .init(top: 10, left: 25, bottom: 0, right: 25), size: .init(width: 0, height: 30))
        
        self.line2.anchor(top: self.stackView.bottomAnchor, leading: self.stackView.leadingAnchor, bottom: nil, trailing: self.stackView.trailingAnchor, padding: .init(top: 10, left: 0, bottom: 0, right: 0), size: .init(width: 0, height: 1))

        self.stackViewtotal.anchor(top: self.line2.bottomAnchor, leading: self.line2.leadingAnchor, bottom: nil, trailing: self.line2.trailingAnchor, padding: .init(top: 10, left: 0, bottom: 0, right: 0))
    }
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
class CheckingCell: UITableViewCell {
    
    let ticketView = UIView()
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.backgroundColor = .clear
        self.selectionStyle = .none
        configure()
    }

    var user: TicketViewModel! {
        didSet {
            
        }
    }
    
    fileprivate func configure() {
        self.addSubview(self.ticketView)
        self.ticketView.fillSuperview()
        
    }

    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
