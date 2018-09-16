//
//  CartoesTableViewCell.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 16/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import UIKit

class CartoesTableViewCell: UITableViewCell {

    @IBOutlet weak var cartaoLbl: UILabel!
    @IBOutlet weak var vencimentoLbl: UILabel!
    @IBOutlet weak var cartaoImgView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setupCell(cartao: Cartao) {
        if let numero = cartao.numero {
            if numero.count > 15 {
                if let finalCart = cartao.numero?.substring(from: .init(encodedOffset: 15)) {
                    self.cartaoLbl.text = "Cartão Final \(finalCart)"
                }
            }
        }
        self.vencimentoLbl.text = "Venc. \(cartao.vencimento!)"
    }
}
