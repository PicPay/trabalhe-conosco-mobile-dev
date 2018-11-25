//
//  CredcardListViewforFooter.swift
//  PicPay
//
//  Created by Marcelo Reis on 11/25/18.
//  Copyright © 2018 Marcelo Reis. All rights reserved.
//

import UIKit

public final class CredcardListViewforFooter: UIView {
    
    // MARK: - IBOutlets
    
    @IBOutlet weak var viewContainer: UIView!
    @IBOutlet weak var btnAddCard: UIButton!
    
    public override func awakeFromNib() {
        viewContainer.layer.cornerRadius = 5
        btnAddCard.setTitle("Adicionar cartão de crédito", for: .normal)
    }
}
