//
//  ValorTableViewCell.swift
//  PicPayTest
//
//  Created by Halisson da Silva Jesus on 16/09/18.
//  Copyright © 2018 Halisson da Silva Jesus. All rights reserved.
//

import UIKit

class ValorTableViewCell: UITableViewCell, TextFieldDelegate {

    @IBOutlet weak var valorTxtFld: UITextField!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func getValueText() -> String {
        return valorTxtFld.text!
    }
    
    func setupCell(_ delegate: PaymentTableViewController) {
        self.valorTxtFld.delegate = delegate
    }
}
