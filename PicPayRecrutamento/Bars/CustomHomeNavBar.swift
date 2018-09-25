//
//  CustomNavBar.swift
//  PicPayRecrutamento
//
//  Created by Sandor ferreira da silva on 13/09/18.
//  Copyright © 2018 Sandor Ferreira da Silva. All rights reserved.
//

import UIKit

class CustomHomeNavBar: UINavigationBar {
    private let saldoLabel : UILabel
    public var balance: UILabel

    public override init(frame: CGRect) {
        
        var childFrame = CGRect(x: frame.width / 3, y: 0, width: frame.width/3, height: frame.height/3)
        saldoLabel = UILabel(frame: childFrame)
        saldoLabel.text = "Meu Saldo"
        saldoLabel.textAlignment = .center
        saldoLabel.font = UIFont.systemFont(ofSize: 11)
        
        childFrame.origin.y += childFrame.height
        childFrame.size.height = 15
        balance = UILabel(frame: childFrame)
        balance.text = "R$0,00" // texto inicial
        balance.textAlignment = .center
        balance.font = UIFont.boldSystemFont(ofSize: 17)
        
        super.init(frame: frame)
        self.shadowImage = UIImage()
        self.setBackgroundImage(UIImage(), for: .default)
        addSubview(saldoLabel)
        addSubview(balance)
        
    }
    
    public func updateBalance(newBalance: String) {
        self.balance.text = "R$\(newBalance)"
        self.reloadInputViews()
    }
    
    @available(*, unavailable)
    public required init?(coder: NSCoder) {
        fatalError("init?(coder:) is not supported")
    }
    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

}
