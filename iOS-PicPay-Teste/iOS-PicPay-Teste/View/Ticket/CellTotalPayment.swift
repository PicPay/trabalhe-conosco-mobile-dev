//
//  CellTotalPayment.swift
//  iOS-PicPay-Teste
//
//  Created by Mac Novo on 05/12/18.
//  Copyright © 2018 Bruno iOS Dev. All rights reserved.
//

import UIKit

class CellTotalPayment: UITableViewCell {

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
    let stackView: UIStackView = {
        let stack = UIStackView()
        stack.axis = .horizontal
        stack.distribution = .fillProportionally
        return stack
    }()
    
    var total: TicketViewModelTotal! {
        didSet {
            self.label.text = total.lavel_total
            self.labeltotal.text = total.value_pay
        }
    }
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        configure()
    }
    fileprivate func configure() {
        self.backgroundColor = .clear
        
        self.addSubview(self.stackView)
        self.stackView.addArrangedSubview(self.label)
        self.stackView.addArrangedSubview(self.labeltotal)
        
        self.stackView.anchor(top: self.topAnchor, leading: self.leadingAnchor, bottom: self.bottomAnchor, trailing: self.trailingAnchor, padding: .init(top: 0, left: 25, bottom: 0, right: 25))
    }
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }

}
