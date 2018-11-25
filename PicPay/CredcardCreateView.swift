//
//  CredcardCreateView.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class CredcardCreateView: UIView {
    
    // MARK: - Properties
    static let storyboardId = "CredcardCreateView"

    // MARK: - Outlets
    @IBOutlet weak var lblCardNumber: UILabel!
    @IBOutlet weak var txtCardNumber: UITextField!
    @IBOutlet weak var lblCardValidate: UILabel!
    @IBOutlet weak var txtCardValidate: UITextField!
    @IBOutlet weak var lblCardCvv: UILabel!
    @IBOutlet weak var txtCardCvv: UITextField!
    @IBOutlet weak var btnRegister: RoundedUIButton!
    
    public override func awakeFromNib() {
        setUp()
    }
    
}

// MARK: - Functions

extension CredcardCreateView {
    fileprivate func setUp() {
        lblCardNumber.text = "Número do cartão"
        txtCardNumber.placeholder = "Informe o número do cartão"
        lblCardValidate.text = "Validate do cartão"
        txtCardValidate.placeholder = "Informe a validade do cartão"
        lblCardCvv.text = "Código de segurança"
        txtCardCvv.placeholder = "Código"
        btnRegister.setTitle("Salvar", for: .normal)
    }
    
    public func applyRedBorder(textField: UITextField) {
        textField.borderStyle = .roundedRect
        textField.layer.borderColor = UIColor.red.cgColor
    }
}
